package com.ahhe.webapi.manufacturing.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SetupOptimizer {

  public OptimizationResult optimize(
      Set<String> requiredFeatureIds, List<CandidateSetup> candidates) {
    return optimize(requiredFeatureIds, candidates, Set.of());
  }

  public OptimizationResult optimize(
      Set<String> requiredFeatureIds,
      List<CandidateSetup> candidates,
      Set<String> initiallyAvailableFixtureFeatures) {
    Set<String> required = Set.copyOf(requiredFeatureIds);
    if (required.isEmpty()) {
      return new OptimizationResult(List.of(), Set.of(), Set.of(), 0);
    }

    List<CandidateSetup> orderedCandidates =
        candidates.stream()
            .sorted(
                Comparator.comparingDouble(CandidateSetup::secondaryCost)
                    .thenComparing(CandidateSetup::id))
            .toList();
    List<CandidateSetup> best = null;
    double bestCost = Double.POSITIVE_INFINITY;

    for (int count = 1; count <= orderedCandidates.size(); count++) {
      List<List<CandidateSetup>> combinations = new ArrayList<>();
      combinations(orderedCandidates, count, 0, new ArrayList<>(), combinations);
      for (List<CandidateSetup> combination : combinations) {
        Set<String> covered = coveredFeatures(combination);
        if (!covered.containsAll(required)) {
          continue;
        }
        List<CandidateSetup> ordered =
            feasibleOrder(combination, initiallyAvailableFixtureFeatures);
        if (ordered.isEmpty()) {
          continue;
        }
        double cost = ordered.stream().mapToDouble(CandidateSetup::secondaryCost).sum();
        if (best == null || cost < bestCost || (cost == bestCost && lexical(ordered, best) < 0)) {
          best = ordered;
          bestCost = cost;
        }
      }
      if (best != null) {
        break;
      }
    }

    if (best == null) {
      Set<String> covered =
          reachableFeatures(orderedCandidates, initiallyAvailableFixtureFeatures);
      Set<String> uncovered = new LinkedHashSet<>(required);
      uncovered.removeAll(covered);
      return new OptimizationResult(List.of(), covered, uncovered, Double.POSITIVE_INFINITY);
    }

    Set<String> covered = coveredFeatures(best);
    Set<String> uncovered = new LinkedHashSet<>(required);
    uncovered.removeAll(covered);
    return new OptimizationResult(best, covered, uncovered, bestCost);
  }

  private void combinations(
      List<CandidateSetup> candidates,
      int targetSize,
      int start,
      List<CandidateSetup> current,
      List<List<CandidateSetup>> result) {
    if (current.size() == targetSize) {
      result.add(List.copyOf(current));
      return;
    }
    for (int index = start; index <= candidates.size() - (targetSize - current.size()); index++) {
      current.add(candidates.get(index));
      combinations(candidates, targetSize, index + 1, current, result);
      current.remove(current.size() - 1);
    }
  }

  private Set<String> coveredFeatures(List<CandidateSetup> setups) {
    Set<String> covered = new HashSet<>();
    setups.forEach(setup -> covered.addAll(setup.coveredFeatureIds()));
    return Set.copyOf(covered);
  }

  private List<CandidateSetup> feasibleOrder(
      List<CandidateSetup> setups, Set<String> initiallyAvailableFixtureFeatures) {
    return feasibleOrder(
        setups,
        new LinkedHashSet<>(initiallyAvailableFixtureFeatures),
        new ArrayList<>());
  }

  private Set<String> reachableFeatures(
      List<CandidateSetup> candidates, Set<String> initiallyAvailableFixtureFeatures) {
    Set<String> available = new LinkedHashSet<>(initiallyAvailableFixtureFeatures);
    Set<String> covered = new LinkedHashSet<>();
    boolean changed;
    do {
      changed = false;
      for (CandidateSetup candidate : candidates) {
        if (available.containsAll(candidate.prerequisiteFeatureIds())
            && covered.addAll(candidate.coveredFeatureIds())) {
          available.addAll(candidate.coveredFeatureIds());
          changed = true;
        }
      }
    } while (changed);
    return Set.copyOf(covered);
  }

  private List<CandidateSetup> feasibleOrder(
      List<CandidateSetup> remaining,
      Set<String> availableFeatures,
      List<CandidateSetup> ordered) {
    if (remaining.isEmpty()) {
      return List.copyOf(ordered);
    }
    List<CandidateSetup> candidates =
        remaining.stream().sorted(Comparator.comparing(CandidateSetup::id)).toList();
    for (CandidateSetup candidate : candidates) {
      if (!availableFeatures.containsAll(candidate.prerequisiteFeatureIds())) {
        continue;
      }
      List<CandidateSetup> nextRemaining = new ArrayList<>(remaining);
      nextRemaining.remove(candidate);
      Set<String> nextAvailable = new LinkedHashSet<>(availableFeatures);
      nextAvailable.addAll(candidate.coveredFeatureIds());
      ordered.add(candidate);
      List<CandidateSetup> result = feasibleOrder(nextRemaining, nextAvailable, ordered);
      ordered.remove(ordered.size() - 1);
      if (!result.isEmpty()) {
        return result;
      }
    }
    return List.of();
  }

  private int lexical(List<CandidateSetup> left, List<CandidateSetup> right) {
    String leftIds = left.stream().map(CandidateSetup::id).sorted().reduce("", String::concat);
    String rightIds = right.stream().map(CandidateSetup::id).sorted().reduce("", String::concat);
    return leftIds.compareTo(rightIds);
  }

  public record CandidateSetup(
      String id,
      String workholdingStrategy,
      String clampReference,
      List<Orientation> indexedOrientations,
      Set<String> coveredFeatureIds,
      Set<String> prerequisiteFeatureIds,
      WorkholdingPlan workholdingPlan,
      double secondaryCost) {

    public CandidateSetup {
      indexedOrientations = List.copyOf(indexedOrientations);
      coveredFeatureIds = Set.copyOf(coveredFeatureIds);
      prerequisiteFeatureIds = Set.copyOf(prerequisiteFeatureIds);
    }

    public CandidateSetup(
        String id,
        String workholdingStrategy,
        String clampReference,
        List<Orientation> indexedOrientations,
        Set<String> coveredFeatureIds,
        double secondaryCost) {
      this(
          id,
          workholdingStrategy,
          clampReference,
          indexedOrientations,
          coveredFeatureIds,
          Set.of(),
          WorkholdingPlan.unspecified(workholdingStrategy, clampReference),
          secondaryCost);
    }
  }

  public record WorkholdingPlan(
      String strategy,
      List<String> locatingFeatures,
      List<String> clampFeatures,
      List<String> exposedFormerClampRegions,
      String workOffset,
      String fixtureDescription,
      String transitionInstructions,
      boolean customFixtureRequired) {

    public WorkholdingPlan {
      locatingFeatures = List.copyOf(locatingFeatures);
      clampFeatures = List.copyOf(clampFeatures);
      exposedFormerClampRegions = List.copyOf(exposedFormerClampRegions);
    }

    public static WorkholdingPlan unspecified(String strategy, String clampReference) {
      return new WorkholdingPlan(
          strategy,
          List.of(clampReference),
          List.of(clampReference),
          List.of(),
          "UNASSIGNED",
          "Fixture details have not yet been generated.",
          "No setup transition is defined.",
          false);
    }
  }

  public record Orientation(
      String id, double bDegrees, double cDegrees, Set<String> coveredFeatureIds) {

    public Orientation {
      coveredFeatureIds = Set.copyOf(coveredFeatureIds);
    }
  }

  public record OptimizationResult(
      List<CandidateSetup> selectedSetups,
      Set<String> coveredFeatureIds,
      Set<String> uncoveredFeatureIds,
      double secondaryCost) {

    public OptimizationResult {
      selectedSetups = List.copyOf(selectedSetups);
      coveredFeatureIds = Set.copyOf(coveredFeatureIds);
      uncoveredFeatureIds = Set.copyOf(uncoveredFeatureIds);
    }

    public boolean complete() {
      return uncoveredFeatureIds.isEmpty();
    }
  }
}
