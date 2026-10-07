package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.GeometryAnalysis;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.ManufacturingOperation;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.StockSelection;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.ToolDefinition;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class ProcessPlanner {

  public Plan createPlan(String requestedMaterial, GeometryAnalysis geometry) {
    Material material = selectMaterial(requestedMaterial);
    Dimensions part = geometry.dimensions();
    Dimensions stock =
        new Dimensions(roundUp(part.xMm() + 8), roundUp(part.yMm() + 8), roundUp(part.zMm() + 6));

    if (stock.xMm() > 500 || stock.yMm() > 500 || stock.zMm() > 400) {
      throw new IllegalArgumentException(
          "The proposed stock exceeds this prototype's conservative UMC-750 work envelope.");
    }

    StockSelection selection =
        new StockSelection(
            requestedMaterial,
            material.grade,
            "Rectangular saw-cut billet",
            stock,
            "A human provides one "
                + material.grade
                + " billet at least "
                + format(stock.xMm())
                + " x "
                + format(stock.yMm())
                + " x "
                + format(stock.zMm())
                + " mm, verifies the material certificate, and deburrs the saw-cut edges.");

    List<ToolDefinition> tools =
        List.of(
            new ToolDefinition(
                1,
                "T01",
                "50 mm indexable face mill",
                50,
                55,
                5,
                "CAT40 face-mill arbor",
                "Face billet and establish Z datum"),
            new ToolDefinition(
                2,
                "T02",
                "12 mm carbide variable-helix end mill",
                12,
                45,
                4,
                "CAT40 ER32 collet holder",
                "Adaptive roughing and profile milling"),
            new ToolDefinition(
                3,
                "T03",
                "6 mm carbide end mill",
                6,
                35,
                4,
                "CAT40 ER20 collet holder",
                "Rest machining and smaller features"),
            new ToolDefinition(
                4,
                "T04",
                "6.8 mm carbide drill",
                6.8,
                50,
                2,
                "CAT40 drill chuck",
                "Representative through-hole cycle"),
            new ToolDefinition(
                5,
                "T05",
                "10 mm 90-degree chamfer mill",
                10,
                40,
                2,
                "CAT40 ER20 collet holder",
                "Edge break and deburring pass"),
            new ToolDefinition(
                6,
                "T06",
                "6 mm ball end mill",
                6,
                45,
                2,
                "CAT40 ER20 slim collet holder",
                "Indexed multi-axis finish demonstration"));

    List<ManufacturingOperation> operations =
        List.of(
            operation(
                10,
                "HUMAN",
                "Preparation",
                "Verify model and drawing",
                "Confirm units, tolerances, datums, finish, and revision. Resolve every warning before "
                    + "physical manufacturing.",
                600),
            operation(
                20,
                "HUMAN",
                "Preparation",
                "Prepare stock and workholding",
                selection.humanInstruction()
                    + " Install approved fixture, indicate it, and record G54.",
                900),
            operation(
                30,
                "HUMAN",
                "Preparation",
                "Load and measure tooling",
                "Load T01-T06 into the listed pockets. Measure length/diameter offsets and verify "
                    + "stickout, holder clearance, runout, and tool condition.",
                900),
            operation(
                40,
                "MACHINE",
                "Physical setup 1 - Orientation A (B0 C0)",
                "Face and rough top-side geometry",
                "Face the billet, adaptive rough the envelope, and machine representative holes.",
                480),
            operation(
                50,
                "HUMAN",
                "In-process inspection",
                "Inspect setup 1",
                "Stop the machine. Verify datums, critical dimensions, remaining stock, inserts, "
                    + "clamping, and chip evacuation.",
                420),
            operation(
                60,
                "MACHINE",
                "Physical setup 1 - Orientation B (B90 C0)",
                "Machine first side",
                "Index the trunnion and finish a representative side using TCPC-style positioning.",
                300),
            operation(
                70,
                "MACHINE",
                "Physical setup 1 - Orientation C (B-35 C180)",
                "Machine opposite accessible features",
                "Re-index within the configured UMC-750 B-axis range and perform finish/chamfer passes.",
                300),
            operation(
                80,
                "HUMAN",
                "Completion",
                "Remove, clean, inspect, and ship",
                "Remove the part, clean away coolant and chips, deburr, perform final inspection, "
                    + "package it, and send it.",
                900));

    return new Plan(selection, tools, operations, material);
  }

  private Material selectMaterial(String requested) {
    String normalized = requested.toLowerCase(Locale.ROOT);
    if (normalized.contains("409") || normalized.contains("stainless")) {
      return new Material("409 stainless steel", 75, 45, 8100, 0.025, 0.06, 0.07, 0.25, 0.3);
    }
    if (normalized.contains("steel")) {
      return new Material(
          "AISI 1018 cold-finished steel", 120, 70, 8100, 0.04, 0.08, 0.1, 0.35, 0.35);
    }
    if (normalized.contains("plastic")
        || normalized.contains("acetal")
        || normalized.contains("delrin")) {
      return new Material(
          "Acetal homopolymer", 250, 100, 8100, 0.08, 0.15, 0.15, 0.75, 0.5);
    }
    return new Material(
        "6061-T6 aluminum", 300, 100, 8100, 0.06, 0.12, 0.12, 0.5, 0.45);
  }

  private ManufacturingOperation operation(
      int sequence,
      String actor,
      String setup,
      String title,
      String description,
      int estimatedSeconds) {
    return new ManufacturingOperation(sequence, actor, setup, title, description, estimatedSeconds);
  }

  private double roundUp(double value) {
    return Math.ceil(value / 5.0) * 5.0;
  }

  private String format(double value) {
    return String.format(Locale.ROOT, "%.0f", value);
  }

  public record Plan(
      StockSelection stock,
      List<ToolDefinition> tools,
      List<ManufacturingOperation> operations,
      Material material) {}

  public record Material(
      String grade,
      int millingSurfaceSpeedMPerMinute,
      int drillingSurfaceSpeedMPerMinute,
      int maxSpindleRpm,
      double endMillChipLoadMm,
      double faceMillChipLoadMm,
      double drillFeedPerRevolutionMm,
      double axialDepthRatio,
      double radialStepoverRatio) {}
}
