package com.ahhe.webapi.manufacturing.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HaasGcodeInterpreterTest {

  private final HaasGcodeInterpreter interpreter = new HaasGcodeInterpreter();

  @Test
  void derivesFeedToolChangeDwellAndRotaryTimeFromProgram() {
    String gcode =
        """
        %
        O1000
        G21 G90 G94
        G00 X0. Y0. Z10.
        T01 M06
        S4000 M03
        G04 P2.0
        G01 X60. F600.
        G00 B90. C180.
        M05
        M30
        %
        """;

    var timing = interpreter.analyze(gcode);

    assertThat(timing.totalSeconds()).isGreaterThan(18);
    assertThat(timing.blocks())
        .anyMatch(block -> block.status().equals("TOOL CHANGE") && block.durationSeconds() == 3.6)
        .anyMatch(block -> block.status().equals("DWELL") && block.durationSeconds() >= 2)
        .anyMatch(
            block ->
                block.raw().contains("G01 X60")
                    && block.durationSeconds() > 5.9
                    && block.durationSeconds() < 6.2)
        .anyMatch(
            block -> block.raw().contains("B90") && block.durationSeconds() >= 3.8);
  }

  @Test
  void modelsG98DrillingFeedAndReturnMotion() {
    String gcode =
        """
        G21 G90 G94
        G00 Z20.
        F120.
        G98 G81 X10. Y0. Z-10. R2.
        X-10.
        G80
        M30
        """;

    var timing = interpreter.analyze(gcode);

    assertThat(timing.cuttingSeconds()).isGreaterThan(12);
    assertThat(timing.blocks())
        .filteredOn(block -> block.status().equals("DRILLING"))
        .hasSize(2)
        .allMatch(block -> block.detail().contains("G98 initial plane"));
  }

  @Test
  void supportsInverseTimeForCombinedLinearAndRotaryFeed() {
    String gcode =
        """
        G21 G90 G93
        G01 X10. B15. F2.
        M30
        """;

    var timing = interpreter.analyze(gcode);

    assertThat(timing.blocks())
        .anyMatch(
            block ->
                block.raw().contains("G01 X10")
                    && Math.abs(block.durationSeconds() - 30) < 0.001);
  }

  @Test
  void preservesHaasIntegerVersusDecimalDwellSemantics() {
    String gcode =
        """
        G04 P10
        G04 P10.
        M30
        """;

    var timing = interpreter.analyze(gcode);

    assertThat(timing.blocks())
        .anyMatch(
            block -> block.raw().equals("G04 P10") && block.durationSeconds() == 0.01)
        .anyMatch(
            block -> block.raw().equals("G04 P10.") && block.durationSeconds() == 10);
  }

  @Test
  void identifiesOperatorStopsWithoutAddingFakeMachineTime() {
    var timing = interpreter.analyze("G21\nM00 (INSPECT PART)\nM30");

    assertThat(timing.blocks())
        .anyMatch(
            block ->
                block.status().equals("OPERATOR STOP")
                    && block.durationSeconds() == 0
                    && block.detail().contains("excluded from machine cycle time"));
  }
}
