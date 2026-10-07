package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.AxisPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class HaasGcodeInterpreter {

  private static final Pattern WORD =
      Pattern.compile("([A-Z])\\s*([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+))");
  private final HaasUmc750MachineProfile profile;

  public HaasGcodeInterpreter() {
    this(HaasUmc750MachineProfile.standard2016Configuration());
  }

  HaasGcodeInterpreter(HaasUmc750MachineProfile profile) {
    this.profile = profile;
  }

  public ProgramTiming analyze(String gcode) {
    List<TimedBlock> blocks = new ArrayList<>();
    State state = new State();
    List<String> lines = gcode.lines().toList();
    double totalSeconds = 0;
    double cuttingSeconds = 0;

    for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
      String raw = lines.get(lineIndex).trim();
      TimedBlock block = analyzeBlock(lineIndex + 1, raw, state);
      blocks.add(block);
      totalSeconds += block.durationSeconds();
      if (block.cutting()) {
        cuttingSeconds += block.durationSeconds();
      }
    }
    return new ProgramTiming(List.copyOf(blocks), totalSeconds, cuttingSeconds, profile.timingBasis());
  }

  private TimedBlock analyzeBlock(int lineNumber, String raw, State state) {
    Snapshot start = state.snapshot();
    String code = stripComments(raw).toUpperCase(Locale.ROOT).trim();
    if (code.isBlank() || code.equals("%") || code.startsWith("O")) {
      return block(lineNumber, raw, "READING", start, state, 0, false, "");
    }

    Words words = Words.parse(code);
    updateModes(words, state);
    double duration = 0;
    boolean cutting = false;
    String status = "RUNNING";
    String detail = "";

    if (words.hasM(6)) {
      String targetTool =
          words.has('T')
              ? "T" + String.format(Locale.ROOT, "%02d", (int) words.last('T'))
              : state.preselectedTool;
      if (state.spindleRpm > 0) {
        duration += state.spindleRpm / profile.spindleAccelerationRpmPerSecond();
        state.spindleRpm = 0;
      }
      if (state.coolantOn) {
        duration += profile.coolantOffSeconds();
        state.coolantOn = false;
      }
      boolean preStaged = targetTool != null && targetTool.equals(state.preselectedTool);
      duration += preStaged ? profile.toolToToolSeconds() : profile.chipToChipSeconds();
      if (targetTool != null) {
        state.activeTool = targetTool;
      }
      state.preselectedTool = null;
      status = "TOOL CHANGE";
      detail =
          preStaged
              ? "Pre-staged tool using Haas 2.8 s average tool-to-tool time"
              : "Non-pre-staged tool using Haas 3.6 s chip-to-chip benchmark";
    } else if (words.has('T')) {
      state.preselectedTool =
          "T" + String.format(Locale.ROOT, "%02d", (int) words.last('T'));
    }

    if (words.hasM(3) || words.hasM(4)) {
      int targetRpm = words.has('S') ? (int) words.last('S') : state.spindleRpm;
      duration +=
          Math.abs(targetRpm - state.spindleRpm) / profile.spindleAccelerationRpmPerSecond();
      state.spindleRpm = targetRpm;
      status = "SPINDLE RAMP";
    } else if (words.hasM(5)) {
      duration += state.spindleRpm / profile.spindleAccelerationRpmPerSecond();
      state.spindleRpm = 0;
      status = "SPINDLE STOP";
    } else if (words.has('S')) {
      state.spindleRpm = (int) words.last('S');
    }

    if (words.hasM(8)) {
      duration += profile.coolantOnSeconds();
      state.coolantOn = true;
      status = "COOLANT ON";
    }
    if (words.hasM(9)) {
      duration += profile.coolantOffSeconds();
      state.coolantOn = false;
      status = "COOLANT OFF";
    }
    if (words.hasM(10) || words.hasM(11) || words.hasM(12) || words.hasM(13)) {
      duration += profile.rotaryBrakeSeconds();
      status = "ROTARY BRAKE";
      detail = "B/C brake transition uses a calibration default; Haas publishes no duration";
    }
    if (words.hasM(0) || words.hasM(1)) {
      status = "OPERATOR STOP";
      detail =
          words.hasM(0)
              ? "Unconditional M00 operator stop; human delay is excluded from machine cycle time"
              : "Optional M01 operator stop; human delay is excluded from machine cycle time";
    }
    if (words.hasG(4)) {
      double dwell =
          words.has('P')
              ? (words.lastTokenHasDecimal('P') ? words.last('P') : words.last('P') / 1000.0)
              : 0;
      duration += Math.max(0, dwell);
      status = "DWELL";
      detail = String.format(Locale.ROOT, "Programmed dwell %.3f s", dwell);
    }

    boolean cycleStart = words.hasG(81) || words.hasG(83);
    if (cycleStart) {
      state.cycle =
          new CannedCycle(
              words.hasG(83) ? 83 : 81,
              coordinate(words, 'Z', state.z, state),
              coordinate(words, 'R', state.z, state),
              words.has('Q') ? words.last('Q') * state.unitScale : 0,
              state.z);
    }

    if (state.cycle != null
        && !words.hasG(80)
        && (cycleStart || hasPositionWords(words))) {
      DrillTiming drill = drillCycle(words, state);
      duration += drill.seconds();
      cutting = true;
      status = state.cycle.code() == 83 ? "PECK DRILLING" : "DRILLING";
      detail = drill.detail();
    } else if (!words.hasG(80) && hasPositionWords(words)) {
      AxisPosition target = target(words, state);
      Motion motion = explicitMotion(words, state.motion);
      double motionSeconds =
          switch (motion) {
            case RAPID -> rapidSeconds(state.position(), target);
            case LINEAR -> feedSeconds(state.position(), target, state);
            case ARC_CW -> arcSeconds(state.position(), target, words, state, true);
            case ARC_CCW -> arcSeconds(state.position(), target, words, state, false);
            case NONE -> 0;
          };
      duration += motionSeconds;
      state.setPosition(target);
      state.motion = motion;
      cutting = motion == Motion.LINEAR || motion == Motion.ARC_CW || motion == Motion.ARC_CCW;
      status = cutting ? "CUTTING" : "RAPID";
      detail =
          cutting
              ? String.format(
                  Locale.ROOT, "G94 feed %.1f mm/min", state.feedMmPerMinute)
              : "Coordinated rapid using the slowest participating axis";
    }

    if (words.hasG(80)) {
      state.cycle = null;
    }
    if (words.hasM(30)) {
      status = "PROGRAM COMPLETE";
    }
    validate(state);
    return block(lineNumber, raw, status, start, state, duration, cutting, detail);
  }

  private void updateModes(Words words, State state) {
    if (words.hasG(20)) {
      state.unitScale = 25.4;
    }
    if (words.hasG(21)) {
      state.unitScale = 1;
    }
    if (words.hasG(90)) {
      state.absolute = true;
    }
    if (words.hasG(91)) {
      state.absolute = false;
    }
    if (words.hasG(93)) {
      state.inverseTime = true;
    }
    if (words.hasG(94)) {
      state.inverseTime = false;
    }
    if (words.hasG(98)) {
      state.returnToInitialPlane = true;
    }
    if (words.hasG(99)) {
      state.returnToInitialPlane = false;
    }
    if (words.has('F')) {
      state.feedMmPerMinute = words.last('F') * (state.inverseTime ? 1 : state.unitScale);
    }
    Motion explicit = explicitMotion(words, state.motion);
    if (explicit != Motion.NONE) {
      state.motion = explicit;
    }
  }

  private DrillTiming drillCycle(Words words, State state) {
    CannedCycle cycle = state.cycle;
    AxisPosition start = state.position();
    double x = coordinate(words, 'X', state.x, state);
    double y = coordinate(words, 'Y', state.y, state);
    AxisPosition xyTarget = new AxisPosition(x, y, state.z, state.b, state.c);
    double seconds = rapidSeconds(start, xyTarget);
    seconds +=
        axisMotionSeconds(
            Math.abs(cycle.rPlane() - state.z),
            profile.linearRapidMmPerMinute() / 60,
            profile.linearAccelerationMmPerSecondSquared());

    double depth = Math.abs(cycle.rPlane() - cycle.depth());
    if (state.feedMmPerMinute <= 0) {
      throw new IllegalArgumentException("Canned cycle requires a positive feed rate.");
    }
    if (cycle.code() == 83 && cycle.peckDepth() > 0) {
      int pecks = Math.max(1, (int) Math.ceil(depth / cycle.peckDepth()));
      double remaining = depth;
      double currentDepth = cycle.rPlane();
      for (int peck = 0; peck < pecks; peck++) {
        double cut = Math.min(cycle.peckDepth(), remaining);
        seconds += feedDistanceSeconds(cut, state);
        currentDepth -= cut;
        remaining -= cut;
        if (remaining > 0) {
          seconds +=
              axisMotionSeconds(
                  Math.abs(currentDepth - cycle.rPlane()),
                  profile.linearRapidMmPerMinute() / 60,
                  profile.linearAccelerationMmPerSecondSquared());
          seconds +=
              axisMotionSeconds(
                  Math.max(0, Math.abs(currentDepth - cycle.rPlane()) - 1),
                  profile.linearRapidMmPerMinute() / 60,
                  profile.linearAccelerationMmPerSecondSquared());
        }
      }
    } else {
      seconds += feedDistanceSeconds(depth, state);
    }

    double returnZ = state.returnToInitialPlane ? cycle.initialPlane() : cycle.rPlane();
    seconds +=
        axisMotionSeconds(
            Math.abs(cycle.depth() - returnZ),
            profile.linearRapidMmPerMinute() / 60,
            profile.linearAccelerationMmPerSecondSquared());
    state.x = x;
    state.y = y;
    state.z = returnZ;
    return new DrillTiming(
        seconds,
        String.format(
            Locale.ROOT,
            "G%d depth %.3f mm, R plane %.3f mm, return %s",
            cycle.code(),
            depth,
            cycle.rPlane(),
            state.returnToInitialPlane ? "G98 initial plane" : "G99 R plane"));
  }

  private double rapidSeconds(AxisPosition start, AxisPosition end) {
    double linearRate = profile.linearRapidMmPerMinute() / 60;
    double linearAcceleration = profile.linearAccelerationMmPerSecondSquared();
    double xTime = axisMotionSeconds(Math.abs(end.x() - start.x()), linearRate, linearAcceleration);
    double yTime = axisMotionSeconds(Math.abs(end.y() - start.y()), linearRate, linearAcceleration);
    double zTime = axisMotionSeconds(Math.abs(end.z() - start.z()), linearRate, linearAcceleration);
    double bTime =
        axisMotionSeconds(
            Math.abs(end.b() - start.b()),
            profile.rotaryRapidDegreesPerSecond(),
            profile.rotaryAccelerationDegreesPerSecondSquared());
    double cTime =
        axisMotionSeconds(
            shortestRotaryDistance(start.c(), end.c()),
            profile.rotaryRapidDegreesPerSecond(),
            profile.rotaryAccelerationDegreesPerSecondSquared());
    double seconds = Math.max(Math.max(Math.max(xTime, yTime), zTime), Math.max(bTime, cTime));
    if (bTime > 0 || cTime > 0) {
      seconds += profile.rotarySettleSeconds();
    }
    return seconds;
  }

  private double feedSeconds(AxisPosition start, AxisPosition end, State state) {
    double linearDistance =
        Math.sqrt(
            square(end.x() - start.x())
                + square(end.y() - start.y())
                + square(end.z() - start.z()));
    double rotaryDistance =
        Math.max(
            Math.abs(end.b() - start.b()), shortestRotaryDistance(start.c(), end.c()));
    if (rotaryDistance > 0 && !state.inverseTime) {
      throw new IllegalArgumentException(
          "Simultaneous linear/rotary feed requires G93 inverse-time feed.");
    }
    if (state.inverseTime) {
      if (state.feedMmPerMinute <= 0) {
        throw new IllegalArgumentException("G93 requires a positive inverse-time F value.");
      }
      return 60.0 / state.feedMmPerMinute;
    }
    return feedDistanceSeconds(linearDistance, state);
  }

  private double arcSeconds(
      AxisPosition start, AxisPosition end, Words words, State state, boolean clockwise) {
    if (!words.has('I') || !words.has('J')) {
      throw new IllegalArgumentException("G02/G03 prototype support requires I and J center offsets.");
    }
    double centerX = start.x() + words.last('I') * state.unitScale;
    double centerY = start.y() + words.last('J') * state.unitScale;
    double radius = Math.hypot(start.x() - centerX, start.y() - centerY);
    double startAngle = Math.atan2(start.y() - centerY, start.x() - centerX);
    double endAngle = Math.atan2(end.y() - centerY, end.x() - centerX);
    double sweep = endAngle - startAngle;
    if (clockwise && sweep >= 0) {
      sweep -= Math.PI * 2;
    }
    if (!clockwise && sweep <= 0) {
      sweep += Math.PI * 2;
    }
    double planarLength = Math.abs(sweep) * radius;
    double helicalLength = Math.hypot(planarLength, end.z() - start.z());
    return feedDistanceSeconds(helicalLength, state);
  }

  private double feedDistanceSeconds(double distance, State state) {
    if (distance <= 0) {
      return 0;
    }
    if (state.feedMmPerMinute <= 0) {
      throw new IllegalArgumentException("Cutting motion requires a positive programmed feed.");
    }
    return axisMotionSeconds(
        distance,
        state.feedMmPerMinute / 60,
        profile.linearAccelerationMmPerSecondSquared());
  }

  private double axisMotionSeconds(double distance, double maximumRate, double acceleration) {
    if (distance <= 0) {
      return 0;
    }
    double accelerationDistance = maximumRate * maximumRate / acceleration;
    if (distance <= accelerationDistance) {
      return 2 * Math.sqrt(distance / acceleration);
    }
    return 2 * maximumRate / acceleration + (distance - accelerationDistance) / maximumRate;
  }

  private AxisPosition target(Words words, State state) {
    return new AxisPosition(
        coordinate(words, 'X', state.x, state),
        coordinate(words, 'Y', state.y, state),
        coordinate(words, 'Z', state.z, state),
        rotaryCoordinate(words, 'B', state.b, state),
        normalizeC(rotaryCoordinate(words, 'C', state.c, state)));
  }

  private double coordinate(Words words, char letter, double current, State state) {
    if (!words.has(letter)) {
      return current;
    }
    double value = words.last(letter) * state.unitScale;
    return state.absolute ? value : current + value;
  }

  private double rotaryCoordinate(Words words, char letter, double current, State state) {
    if (!words.has(letter)) {
      return current;
    }
    double value = words.last(letter);
    return state.absolute ? value : current + value;
  }

  private Motion explicitMotion(Words words, Motion current) {
    if (words.hasG(0)) {
      return Motion.RAPID;
    }
    if (words.hasG(1)) {
      return Motion.LINEAR;
    }
    if (words.hasG(2)) {
      return Motion.ARC_CW;
    }
    if (words.hasG(3)) {
      return Motion.ARC_CCW;
    }
    return current;
  }

  private boolean hasPositionWords(Words words) {
    return words.has('X')
        || words.has('Y')
        || words.has('Z')
        || words.has('B')
        || words.has('C');
  }

  private void validate(State state) {
    List<String> alarms = new ArrayList<>();
    if (Math.abs(state.x) > profile.xTravelMm() / 2) {
      alarms.add("X travel exceeded");
    }
    if (Math.abs(state.y) > profile.yTravelMm() / 2) {
      alarms.add("Y travel exceeded");
    }
    if (state.z < -profile.zTravelMm() || state.z > profile.zTravelMm()) {
      alarms.add("Z travel exceeded");
    }
    if (state.b < profile.bMinimumDegrees() || state.b > profile.bMaximumDegrees()) {
      alarms.add("B travel exceeded");
    }
    if (state.spindleRpm > profile.maximumSpindleRpm()) {
      alarms.add("Standard spindle RPM exceeded");
    }
    if (!state.inverseTime
        && state.feedMmPerMinute > profile.maximumProgrammedFeedMmPerMinute()) {
      alarms.add("G94 maximum programmed feed exceeded");
    }
    if (!alarms.isEmpty()) {
      throw new IllegalStateException(
          "Machine envelope validation failed: " + String.join(", ", alarms));
    }
  }

  private TimedBlock block(
      int lineNumber,
      String raw,
      String status,
      Snapshot start,
      State state,
      double duration,
      boolean cutting,
      String detail) {
    return new TimedBlock(
        lineNumber,
        raw,
        status,
        start.position(),
        state.position(),
        start.spindleRpm(),
        state.spindleRpm,
        state.feedMmPerMinute,
        state.activeTool,
        Math.max(0, duration),
        cutting,
        detail);
  }

  private String stripComments(String block) {
    StringBuilder result = new StringBuilder();
    int depth = 0;
    for (int index = 0; index < block.length(); index++) {
      char current = block.charAt(index);
      if (current == '(') {
        depth++;
      } else if (current == ')' && depth > 0) {
        depth--;
      } else if (depth == 0 && current == ';') {
        break;
      } else if (depth == 0) {
        result.append(current);
      }
    }
    return result.toString();
  }

  private double shortestRotaryDistance(double start, double end) {
    double delta = Math.abs(normalizeC(end) - normalizeC(start));
    return Math.min(delta, 360 - delta);
  }

  private double normalizeC(double value) {
    double normalized = value % 360;
    return normalized < 0 ? normalized + 360 : normalized;
  }

  private double square(double value) {
    return value * value;
  }

  public record ProgramTiming(
      List<TimedBlock> blocks,
      double totalSeconds,
      double cuttingSeconds,
      String timingBasis) {}

  public record TimedBlock(
      int lineNumber,
      String raw,
      String status,
      AxisPosition start,
      AxisPosition end,
      int startSpindleRpm,
      int endSpindleRpm,
      double feedMmPerMinute,
      String activeTool,
      double durationSeconds,
      boolean cutting,
      String detail) {}

  private record CannedCycle(
      int code, double depth, double rPlane, double peckDepth, double initialPlane) {}

  private record DrillTiming(double seconds, String detail) {}

  private record Snapshot(AxisPosition position, int spindleRpm) {}

  private enum Motion {
    NONE,
    RAPID,
    LINEAR,
    ARC_CW,
    ARC_CCW
  }

  private static final class State {
    private double x;
    private double y;
    private double z;
    private double b;
    private double c;
    private double unitScale = 1;
    private double feedMmPerMinute;
    private int spindleRpm;
    private boolean absolute = true;
    private boolean inverseTime;
    private boolean returnToInitialPlane = true;
    private String activeTool = "None";
    private String preselectedTool;
    private boolean coolantOn;
    private Motion motion = Motion.NONE;
    private CannedCycle cycle;

    private AxisPosition position() {
      return new AxisPosition(x, y, z, b, c);
    }

    private void setPosition(AxisPosition position) {
      x = position.x();
      y = position.y();
      z = position.z();
      b = position.b();
      c = position.c();
    }

    private Snapshot snapshot() {
      return new Snapshot(position(), spindleRpm);
    }
  }

  private static final class Words {
    private final List<Word> values;

    private Words(List<Word> values) {
      this.values = values;
    }

    private static Words parse(String block) {
      Matcher matcher = WORD.matcher(block);
      List<Word> words = new ArrayList<>();
      while (matcher.find()) {
        words.add(
            new Word(
                matcher.group(1).charAt(0),
                Double.parseDouble(matcher.group(2)),
                matcher.group(2)));
      }
      return new Words(words);
    }

    private boolean has(char letter) {
      return values.stream().anyMatch(word -> word.letter() == letter);
    }

    private double last(char letter) {
      for (int index = values.size() - 1; index >= 0; index--) {
        Word word = values.get(index);
        if (word.letter() == letter) {
          return word.value();
        }
      }
      throw new IllegalArgumentException("Missing G-code word " + letter);
    }

    private boolean hasG(int code) {
      return values.stream()
          .anyMatch(word -> word.letter() == 'G' && Math.round(word.value()) == code);
    }

    private boolean hasM(int code) {
      return values.stream()
          .anyMatch(word -> word.letter() == 'M' && Math.round(word.value()) == code);
    }

    private boolean lastTokenHasDecimal(char letter) {
      for (int index = values.size() - 1; index >= 0; index--) {
        Word word = values.get(index);
        if (word.letter() == letter) {
          return word.token().contains(".");
        }
      }
      return false;
    }
  }

  private record Word(char letter, double value, String token) {}
}
