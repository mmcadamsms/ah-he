package com.ahhe.webapi.manufacturing.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ahhe.webapi.manufacturing.service.SetupOptimizer.CandidateSetup;
import com.ahhe.webapi.manufacturing.service.SetupOptimizer.OptimizationResult;
import com.ahhe.webapi.manufacturing.service.SetupOptimizer.WorkholdingPlan;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SetupOptimizerTest {

  private final SetupOptimizer optimizer = new SetupOptimizer();

  @Test
  void selectsOneSetupWhenOneClampingCoversEveryRequiredFeature() {
    Set<String> required = Set.of("top-pocket", "side-hole", "rear-slot");
    CandidateSetup oneClamp =
        setup("vise-a", Set.of("top-pocket", "side-hole", "rear-slot"), 12);
    CandidateSetup redundant = setup("vise-b", Set.of("top-pocket"), 4);

    OptimizationResult result = optimizer.optimize(required, List.of(redundant, oneClamp));

    assertThat(result.complete()).isTrue();
    assertThat(result.selectedSetups()).extracting(CandidateSetup::id).containsExactly("vise-a");
  }

  @Test
  void addsOnlyTheMinimumSetupsRequiredForCompleteCoverage() {
    Set<String> required = Set.of("top", "left", "right", "bottom");
    List<CandidateSetup> candidates =
        List.of(
            setup("top-clamp", Set.of("top", "left", "right"), 10),
            setup("flip-clamp", Set.of("bottom", "left", "right"), 8),
            setup("left-clamp", Set.of("top", "bottom"), 20),
            setup("right-clamp", Set.of("left"), 1));

    OptimizationResult result = optimizer.optimize(required, candidates);

    assertThat(result.complete()).isTrue();
    assertThat(result.selectedSetups()).hasSize(2);
    assertThat(result.selectedSetups())
        .extracting(CandidateSetup::id)
        .containsExactlyInAnyOrder("top-clamp", "flip-clamp");
  }

  @Test
  void reportsFeaturesThatNoCandidateCanMachine() {
    OptimizationResult result =
        optimizer.optimize(
            Set.of("top", "undercut"),
            List.of(setup("top-clamp", Set.of("top"), 1)));

    assertThat(result.complete()).isFalse();
    assertThat(result.uncoveredFeatureIds()).containsExactly("undercut");
  }

  @Test
  void ordersSetupsSoEarlierMachiningCreatesLaterWorkholdingFeatures() {
    CandidateSetup first =
        setupWithPrerequisites(
            "machine-bore",
            Set.of("finished-bore", "top-features"),
            Set.of("raw-stock"),
            5);
    CandidateSetup second =
        setupWithPrerequisites(
            "expanding-mandrel",
            Set.of("former-clamp-face"),
            Set.of("finished-bore"),
            4);

    OptimizationResult result =
        optimizer.optimize(
            Set.of("top-features", "former-clamp-face"),
            List.of(second, first),
            Set.of("raw-stock"));

    assertThat(result.complete()).isTrue();
    assertThat(result.selectedSetups())
        .extracting(CandidateSetup::id)
        .containsExactly("machine-bore", "expanding-mandrel");
  }

  @Test
  void rejectsCoverageWhoseWorkholdingPrerequisiteCanNeverBeCreated() {
    CandidateSetup impossible =
        setupWithPrerequisites(
            "mandrel",
            Set.of("bottom"),
            Set.of("missing-finished-bore"),
            1);

    OptimizationResult result =
        optimizer.optimize(Set.of("bottom"), List.of(impossible), Set.of("raw-stock"));

    assertThat(result.complete()).isFalse();
    assertThat(result.selectedSetups()).isEmpty();
  }

  private CandidateSetup setup(String id, Set<String> coverage, double cost) {
    return new CandidateSetup(id, "test fixture", id, List.of(), coverage, cost);
  }

  private CandidateSetup setupWithPrerequisites(
      String id, Set<String> coverage, Set<String> prerequisites, double cost) {
    return new CandidateSetup(
        id,
        "test fixture",
        id,
        List.of(),
        coverage,
        prerequisites,
        new WorkholdingPlan(
            "test fixture",
            List.of(id + "-datum"),
            List.of(id + "-clamp"),
            List.of(),
            "G54",
            "Test fixture",
            "Transfer part",
            false),
        cost);
  }
}
