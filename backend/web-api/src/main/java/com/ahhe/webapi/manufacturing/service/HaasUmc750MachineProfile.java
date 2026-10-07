package com.ahhe.webapi.manufacturing.service;

public record HaasUmc750MachineProfile(
    String name,
    double xTravelMm,
    double yTravelMm,
    double zTravelMm,
    double bMinimumDegrees,
    double bMaximumDegrees,
    double maximumSpindleRpm,
    double linearRapidMmPerMinute,
    double maximumProgrammedFeedMmPerMinute,
    double rotaryRapidDegreesPerSecond,
    double toolToToolSeconds,
    double chipToChipSeconds,
    double linearAccelerationMmPerSecondSquared,
    double rotaryAccelerationDegreesPerSecondSquared,
    double spindleAccelerationRpmPerSecond,
    double rotarySettleSeconds,
    double rotaryBrakeSeconds,
    double coolantOnSeconds,
    double coolantOffSeconds,
    String timingBasis) {

  public static HaasUmc750MachineProfile standard2016Configuration() {
    return new HaasUmc750MachineProfile(
        "2016 Haas UMC-750",
        762,
        508,
        508,
        -35,
        110,
        8100,
        22_860,
        16_510,
        50,
        2.8,
        3.6,
        2_500,
        100,
        2_000,
        0.2,
        0.5,
        1.0,
        0.5,
        "2015/2016 Haas documentation and current official specifications: 900 ipm linear "
            + "rapid, 650 ipm maximum G94 feed, 50 deg/s rotary rapid, 8100 rpm spindle, "
            + "2.8 s average tool-to-tool, and 3.6 s chip-to-chip benchmark. Acceleration, "
            + "spindle ramp, rotary brake/settle, and coolant latency are calibration defaults.");
  }
}
