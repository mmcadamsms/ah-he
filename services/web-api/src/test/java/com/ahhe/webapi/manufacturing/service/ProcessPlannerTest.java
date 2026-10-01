package com.ahhe.webapi.manufacturing.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.GeometryAnalysis;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProcessPlannerTest {

  private final ProcessPlanner planner = new ProcessPlanner();

  @Test
  void selectsStockToolsAndHumanOperations() {
    var geometry = new GeometryAnalysis("STEP", new Dimensions(80, 40, 12), 20, List.of());

    var plan = planner.createPlan("aluminum", geometry);

    assertThat(plan.stock().selectedGrade()).isEqualTo("6061-T6 aluminum");
    assertThat(plan.stock().dimensions()).isEqualTo(new Dimensions(90, 50, 20));
    assertThat(plan.tools()).hasSize(6);
    assertThat(plan.operations())
        .anyMatch(operation -> operation.actor().equals("HUMAN"))
        .anyMatch(operation -> operation.actor().equals("MACHINE"));
  }
}

