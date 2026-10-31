package com.ahhe.operations.service;

import static com.ahhe.operations.model.OperationContext.Role.COMMERCIAL;
import static com.ahhe.operations.service.BusinessRecords.*;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationContext.Outcome;
import com.ahhe.operations.model.OperationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class CommercialService {
  private final OperationEngine engine;

  public CommercialService(OperationEngine engine) {
    this.engine = engine;
  }

  public record ComparisonInput(
      String id,
      List<String> quoteIds,
      List<BigDecimal> adjustments,
      List<String> adjustmentReasons,
      BigDecimal internalCost) {}

  public Outcome benchmark(OperationContext context, Benchmark input) {
    return engine.execute(
        context,
        COMMERCIAL,
        "benchmark.register-owned-source",
        input,
        unit -> {
          unit.requireInternal();
          unit.requireBeneficiaryCustomer("");
          InventoryService.evidence(unit, List.of(input.geometryArtifactId()), true);
          if (!unit.load("artifact", input.geometryArtifactId(), Artifact.class)
              .customerId()
              .isEmpty()) {
            throw OperationException.unresolved(
                "The manual pilot accepts company-owned benchmark artifacts only.");
          }
          unit.create("benchmark", input.id(), "", input);
        });
  }

  public Outcome quote(OperationContext context, Quote input) {
    return engine.execute(
        context,
        COMMERCIAL,
        "quote.record-manual-authorized",
        input,
        unit -> {
          unit.requireInternal();
          unit.requireBeneficiaryCustomer("");
          unit.load("benchmark", input.benchmarkId(), Benchmark.class);
          unit.expect("benchmark", input.benchmarkId(), input.benchmarkRevision());
          InventoryService.evidence(unit, List.of(input.evidenceId()), true);
          if (input.quotedAt().isAfter(engine.now())) {
            throw new IllegalArgumentException("Quote timestamps cannot be in the future.");
          }
          unit.create("quote", input.id(), "", input);
        });
  }

  public Outcome compare(OperationContext context, ComparisonInput input) {
    return engine.execute(
        context,
        COMMERCIAL,
        "quote.compare-with-explicit-adjustments",
        input,
        unit -> {
          unit.requireInternal();
          unit.requireBeneficiaryCustomer("");
          Checks.id(input.id());
          Checks.bounded(input.quoteIds(), 10);
          Checks.nonnegative(input.internalCost());
          if (input.quoteIds().size() < 2
              || input.adjustments().size() != input.quoteIds().size()
              || input.adjustmentReasons().size() != input.quoteIds().size()) {
            throw new IllegalArgumentException(
                "Comparison requires 2-10 quotes and aligned explicit adjustments.");
          }
          var quotes =
              input.quoteIds().stream().map(id -> unit.load("quote", id, Quote.class)).toList();
          var first = quotes.getFirst();
          var differences = new ArrayList<String>();
          var raw = new ArrayList<BigDecimal>();
          var adjusted = new ArrayList<BigDecimal>();
          for (int index = 0; index < quotes.size(); index++) {
            var quote = quotes.get(index);
            unit.boundValidity(quote.expiresAt());
            unit.load("benchmark", quote.benchmarkId(), Benchmark.class);
            unit.expect("benchmark", quote.benchmarkId(), quote.benchmarkRevision());
            if (!quote.benchmarkId().equals(first.benchmarkId())
                || !quote.currency().equals(first.currency())
                || !engine.now().isBefore(quote.expiresAt())) {
              throw OperationException.unresolved(
                  "Quote is expired or cannot be compared in the same benchmark/currency.");
            }
            var total = quote.price().add(quote.shipping()).add(quote.tax());
            var adjustment = Checks.decimal(input.adjustments().get(index));
            Checks.text(input.adjustmentReasons().get(index), 1000);
            if (total.add(adjustment).signum() < 0) {
              throw new IllegalArgumentException("Adjusted price cannot be negative.");
            }
            raw.add(total);
            adjusted.add(total.add(adjustment));
            if (quote.quantity().compareTo(first.quantity()) != 0)
              differences.add(quote.id() + ": quantity");
            if (!quote.material().equals(first.material()))
              differences.add(quote.id() + ": material");
            if (!quote.tolerance().equals(first.tolerance()))
              differences.add(quote.id() + ": tolerance");
            if (!quote.finish().equals(first.finish())) differences.add(quote.id() + ": finish");
            if (quote.leadDays() != first.leadDays()) differences.add(quote.id() + ": lead time");
            if (quote.shipping().compareTo(first.shipping()) != 0)
              differences.add(quote.id() + ": shipping");
            if (quote.tax().compareTo(first.tax()) != 0) differences.add(quote.id() + ": tax");
          }
          unit.create(
              "comparison",
              input.id(),
              "",
              new QuoteComparison(
                  input.id(),
                  List.copyOf(input.quoteIds()),
                  differences,
                  raw,
                  List.copyOf(input.adjustments()),
                  List.copyOf(input.adjustmentReasons()),
                  adjusted,
                  input.internalCost(),
                  first.currency(),
                  engine.now()));
        });
  }
}
