package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.ProgramManifest;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.ProgramSection;
import com.ahhe.webapi.manufacturing.service.ProcessPlanner.Material;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class GcodeGenerator {

  private static final double FACE_DIAMETER_MM = 50;
  private static final int FACE_EDGES = 5;
  private static final double ROUGH_DIAMETER_MM = 12;
  private static final int ROUGH_FLUTES = 4;
  private static final double FINISH_DIAMETER_MM = 6;
  private static final int FINISH_FLUTES = 4;
  private static final double DRILL_DIAMETER_MM = 6.8;

  public String generate(Dimensions part, Dimensions stock, Material material) {
    return generateProgram(part, stock, material).gcode();
  }

  public GeneratedProgram generateProgram(
      Dimensions part, Dimensions stock, Material material) {
    CuttingParameters face =
        millingParameters(
            material,
            FACE_DIAMETER_MM,
            FACE_EDGES,
            material.faceMillChipLoadMm(),
            0.7);
    CuttingParameters rough =
        millingParameters(
            material,
            ROUGH_DIAMETER_MM,
            ROUGH_FLUTES,
            material.endMillChipLoadMm(),
            1.0);
    CuttingParameters finish =
        millingParameters(
            material,
            FINISH_DIAMETER_MM,
            FINISH_FLUTES,
            material.endMillChipLoadMm() * 0.55,
            1.1);
    CuttingParameters ball =
        millingParameters(
            material,
            FINISH_DIAMETER_MM,
            2,
            material.endMillChipLoadMm() * 0.45,
            1.0);
    CuttingParameters drill = drillingParameters(material);

    List<String> blocks = new ArrayList<>();
    blocks.add("%");
    blocks.add("O1001 (AH-HE UMC-750 3+2 INDEXED DEMONSTRATION)");
    blocks.add("(NOT FOR MACHINE EXECUTION - CAM AND MACHINIST VALIDATION REQUIRED)");
    blocks.add("(PART ENVELOPE " + dimensions(part) + " MM)");
    blocks.add("(STOCK " + dimensions(stock) + " MM)");
    blocks.add("(MATERIAL " + material.grade().toUpperCase(Locale.ROOT) + ")");
    blocks.add("(PROGRAM STRUCTURE: 1 PHYSICAL CLAMPING, 3 AUTOMATIC B/C ORIENTATIONS)");
    blocks.add("(NO PART FLIP OR RE-CLAMP IS MODELED BY THIS PROGRAM)");
    blocks.add("(M00 AFTER ORIENTATION A REQUIRES OPERATOR INSPECTION AND RESUME)");
    addParameterComment(blocks, "T01 FACE", face, material.faceMillChipLoadMm());
    addParameterComment(blocks, "T02 ROUGH", rough, material.endMillChipLoadMm());
    addParameterComment(blocks, "T03 FINISH", finish, material.endMillChipLoadMm() * 0.55);
    blocks.add(
        "(T04 DRILL VC "
            + material.drillingSurfaceSpeedMPerMinute()
            + " M/MIN, "
            + drill.rpm()
            + " RPM, "
            + drill.feedMmPerMinute()
            + " MM/MIN)");
    addParameterComment(blocks, "T06 BALL", ball, material.endMillChipLoadMm() * 0.45);
    blocks.add("G21 G90 G17 G40 G49 G80 G94");
    blocks.add("G53 G00 Z0.");

    blocks.add("(===== ORIENTATION A START: TOP, B0 C0, G54 =====)");
    blocks.add("(PURPOSE: FACE STOCK, ROUGH TOP FEATURES, FINISH PROFILE, DRILL HOLES)");
    blocks.add("(OPERATOR: NO RE-CLAMP; VERIFY G54 AND FIXTURE CLEARANCE BEFORE CYCLE START)");
    blocks.add("T01 M06");
    blocks.add("T02 (PRE-CALL NEXT TOOL)");
    blocks.add("S" + face.rpm() + " M03");
    blocks.add("G04 P1.0 (SPINDLE STABILIZE)");
    blocks.add("G54 G00 B0. C0.");
    blocks.add("G43 H01 Z50. M08");
    addFacingPasses(blocks, stock, face);
    blocks.add("G00 Z50.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");

    blocks.add("T02 M06");
    blocks.add("T03 (PRE-CALL NEXT TOOL)");
    blocks.add("S" + rough.rpm() + " M03");
    blocks.add("G04 P1.0");
    blocks.add("G54 G00 B0. C0.");
    blocks.add("G43 H02 Z25. M08");
    addRasterRoughing(blocks, part, material, rough);
    blocks.add("G00 Z25.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");

    blocks.add("T03 M06");
    blocks.add("T04 (PRE-CALL NEXT TOOL)");
    blocks.add("S" + finish.rpm() + " M03");
    blocks.add("G04 P0.8");
    blocks.add("G54 G00 B0. C0.");
    blocks.add("G43 H03 Z25. M08");
    addContourFinish(blocks, part, finish);
    blocks.add("G00 Z25.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");

    blocks.add("T04 M06");
    blocks.add("T03 (PRE-CALL NEXT TOOL)");
    blocks.add("S" + drill.rpm() + " M03");
    blocks.add("G04 P0.8");
    blocks.add("G54 G00 B0. C0.");
    blocks.add("G43 H04 Z25. M08");
    addDrilling(blocks, part, drill);
    blocks.add("G00 Z50.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");
    blocks.add("(===== ORIENTATION A COMPLETE =====)");
    blocks.add("(OPERATOR INSPECTION: DIMENSIONS, CLAMPING, TOOL CONDITION, CHIP CLEARANCE)");
    blocks.add("M00 (INSPECTION REQUIRED - RESUME WHEN SAFE)");

    blocks.add("(===== ORIENTATION B START: INDEX B90 C0, SAME PHYSICAL CLAMPING =====)");
    blocks.add("(PURPOSE: FINISH FIRST ACCESSIBLE SIDE)");
    blocks.add("(AUTOMATIC INDEX: NO OPERATOR RE-CLAMP OR WORK-OFFSET CHANGE)");
    blocks.add("T03 M06");
    blocks.add("T06 (PRE-CALL NEXT TOOL)");
    blocks.add("S" + finish.rpm() + " M03");
    blocks.add("G04 P0.8");
    blocks.add("M11 (RELEASE B BRAKE)");
    blocks.add("M13 (RELEASE C BRAKE)");
    blocks.add("G54 G00 B90. C0.");
    blocks.add("M10 (ENGAGE B BRAKE)");
    blocks.add("M12 (ENGAGE C BRAKE)");
    blocks.add("G43 H03 Z40. M08");
    addIndexedSidePasses(blocks, part, finish, 1);
    blocks.add("G00 Z40.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");
    blocks.add("(===== ORIENTATION B COMPLETE =====)");

    blocks.add("(===== ORIENTATION C START: INDEX B-35 C180, SAME PHYSICAL CLAMPING =====)");
    blocks.add("(PURPOSE: FINISH OPPOSITE ACCESSIBLE SIDE)");
    blocks.add("(LIMIT: UNDERSIDE OR CLAMPED SURFACES ARE NOT COMPLETED BY THIS PROGRAM)");
    blocks.add("T06 M06");
    blocks.add("S" + ball.rpm() + " M03");
    blocks.add("G04 P0.8");
    blocks.add("M11 (RELEASE B BRAKE)");
    blocks.add("M13 (RELEASE C BRAKE)");
    blocks.add("G54 G00 B-35. C180.");
    blocks.add("M10 (ENGAGE B BRAKE)");
    blocks.add("M12 (ENGAGE C BRAKE)");
    blocks.add("G43 H06 Z35. M08");
    addIndexedSidePasses(blocks, part, ball, -1);
    blocks.add("G00 Z35.");
    blocks.add("M09");
    blocks.add("G53 G00 Z0.");
    blocks.add("(===== ORIENTATION C COMPLETE =====)");
    blocks.add("G53 G00 X0. Y0.");
    blocks.add("M05");
    blocks.add("M30");
    blocks.add("%");
    ProgramManifest manifest =
        new ProgramManifest(
            "O1001",
            1,
            3,
            1,
            "One physical workholding setup with three automatic 3+2 indexed orientations. "
                + "This is legitimately one NC program because the part remains clamped and G54 "
                + "does not change. A real flip/re-clamp would require another physical setup, "
                + "a new verified work offset, and normally a separate NC program.",
            List.of(
                new ProgramSection(
                    1,
                    "Orientation A - top",
                    "B0 C0",
                    "G54",
                    "Verify initial clamping and work offset; inspect at the following M00."),
                new ProgramSection(
                    2,
                    "Orientation B - first side",
                    "B90 C0",
                    "G54",
                    "None; automatic brake release, index, clamp, and machining."),
                new ProgramSection(
                    3,
                    "Orientation C - opposite accessible side",
                    "B-35 C180",
                    "G54",
                    "None; automatic index. Clamped/underside surfaces remain out of scope.")));
    return new GeneratedProgram(String.join("\n", blocks), manifest);
  }

  private void addFacingPasses(
      List<String> blocks, Dimensions stock, CuttingParameters parameters) {
    double xStart = -stock.xMm() / 2 - FACE_DIAMETER_MM * 0.55;
    double xEnd = stock.xMm() / 2 + FACE_DIAMETER_MM * 0.55;
    double yStart = -stock.yMm() / 2;
    double yEnd = stock.yMm() / 2;
    double stepover = FACE_DIAMETER_MM * 0.65;
    blocks.add("G00 X" + f(xStart) + " Y" + f(yStart) + " Z5.");
    blocks.add("G01 Z0. F250.");
    boolean forward = true;
    for (double y = yStart; y <= yEnd + 0.001; y += stepover) {
      blocks.add(
          "G01 X"
              + f(forward ? xEnd : xStart)
              + " Y"
              + f(Math.min(y, yEnd))
              + " F"
              + parameters.feedMmPerMinute()
              + ".");
      forward = !forward;
    }
  }

  private void addRasterRoughing(
      List<String> blocks,
      Dimensions part,
      Material material,
      CuttingParameters parameters) {
    double totalDepth = Math.max(1, Math.min(part.zMm() * 0.6, part.zMm() - 1));
    double axialStep =
        Math.max(0.75, Math.min(ROUGH_DIAMETER_MM * material.axialDepthRatio(), totalDepth));
    double radialStep = Math.max(1.5, ROUGH_DIAMETER_MM * material.radialStepoverRatio());
    double xStart = -part.xMm() / 2 + ROUGH_DIAMETER_MM / 2;
    double xEnd = part.xMm() / 2 - ROUGH_DIAMETER_MM / 2;
    double yStart = -part.yMm() / 2 + ROUGH_DIAMETER_MM / 2;
    double yEnd = part.yMm() / 2 - ROUGH_DIAMETER_MM / 2;
    if (xEnd <= xStart || yEnd <= yStart) {
      xStart = -part.xMm() * 0.35;
      xEnd = part.xMm() * 0.35;
      yStart = -part.yMm() * 0.35;
      yEnd = part.yMm() * 0.35;
    }

    double depth = 0;
    int level = 1;
    while (depth < totalDepth - 0.001) {
      depth = Math.min(totalDepth, depth + axialStep);
      blocks.add("(ROUGH LEVEL " + level + " Z-" + f(depth) + ")");
      blocks.add("G00 X" + f(xStart) + " Y" + f(yStart) + " Z2.");
      blocks.add("G01 Z-" + f(depth) + " F" + Math.max(100, parameters.feedMmPerMinute() / 4) + ".");
      boolean forward = true;
      for (double y = yStart; y <= yEnd + 0.001; y += radialStep) {
        blocks.add(
            "G01 X"
                + f(forward ? xEnd : xStart)
                + " Y"
                + f(Math.min(y, yEnd))
                + " F"
                + parameters.feedMmPerMinute()
                + ".");
        forward = !forward;
      }
      blocks.add("G00 Z2.");
      level++;
    }
  }

  private void addContourFinish(
      List<String> blocks, Dimensions part, CuttingParameters parameters) {
    double x = Math.max(1, part.xMm() / 2 - FINISH_DIAMETER_MM / 2);
    double y = Math.max(1, part.yMm() / 2 - FINISH_DIAMETER_MM / 2);
    double depth = Math.max(0.5, Math.min(part.zMm() * 0.65, part.zMm() - 0.5));
    blocks.add("G00 X-" + f(x) + " Y-" + f(y) + " Z2.");
    blocks.add("G01 Z-" + f(depth) + " F" + Math.max(80, parameters.feedMmPerMinute() / 4) + ".");
    blocks.add("G01 X" + f(x) + " F" + parameters.feedMmPerMinute() + ".");
    blocks.add("Y" + f(y) + ".");
    blocks.add("X-" + f(x) + ".");
    blocks.add("Y-" + f(y) + ".");
  }

  private void addDrilling(
      List<String> blocks, Dimensions part, CuttingParameters parameters) {
    double depth = Math.max(2, Math.min(part.zMm() * 0.8, part.zMm() - 0.5));
    double holeX = Math.max(2, part.xMm() * 0.25);
    if (depth > DRILL_DIAMETER_MM * 3) {
      blocks.add(
          "G98 G83 X-"
              + f(holeX)
              + " Y0. Z-"
              + f(depth)
              + " R2. Q"
              + f(DRILL_DIAMETER_MM * 0.8)
              + " F"
              + parameters.feedMmPerMinute()
              + ".");
    } else {
      blocks.add(
          "G98 G81 X-"
              + f(holeX)
              + " Y0. Z-"
              + f(depth)
              + " R2. F"
              + parameters.feedMmPerMinute()
              + ".");
    }
    blocks.add("X" + f(holeX) + " Y0.");
    blocks.add("G80");
  }

  private void addIndexedSidePasses(
      List<String> blocks,
      Dimensions part,
      CuttingParameters parameters,
      int direction) {
    double xStart = -part.xMm() * 0.35;
    double xEnd = part.xMm() * 0.35;
    double yStart = -part.yMm() * 0.3;
    double yEnd = part.yMm() * 0.3;
    double depth = Math.max(0.5, Math.min(2, part.zMm() * 0.15));
    blocks.add("G00 X" + f(xStart) + " Y" + f(direction * yStart) + " Z3.");
    blocks.add("G01 Z-" + f(depth) + " F" + Math.max(75, parameters.feedMmPerMinute() / 4) + ".");
    for (int pass = 0; pass < 5; pass++) {
      double fraction = pass / 4.0;
      double y = yStart + (yEnd - yStart) * fraction;
      blocks.add(
          "G01 X"
              + f(pass % 2 == 0 ? xEnd : xStart)
              + " Y"
              + f(direction * y)
              + " F"
              + parameters.feedMmPerMinute()
              + ".");
    }
  }

  private CuttingParameters millingParameters(
      Material material,
      double diameterMm,
      int cuttingEdges,
      double chipLoadMm,
      double surfaceSpeedMultiplier) {
    int rpm =
        rpm(
            (int) Math.round(material.millingSurfaceSpeedMPerMinute() * surfaceSpeedMultiplier),
            diameterMm,
            material.maxSpindleRpm());
    int feed = Math.max(50, (int) Math.round(rpm * chipLoadMm * cuttingEdges));
    return new CuttingParameters(rpm, feed);
  }

  private CuttingParameters drillingParameters(Material material) {
    int rpm =
        rpm(
            material.drillingSurfaceSpeedMPerMinute(),
            DRILL_DIAMETER_MM,
            material.maxSpindleRpm());
    int feed = Math.max(40, (int) Math.round(rpm * material.drillFeedPerRevolutionMm()));
    return new CuttingParameters(rpm, feed);
  }

  private void addParameterComment(
      List<String> blocks,
      String tool,
      CuttingParameters parameters,
      double chipLoad) {
    blocks.add(
        "("
            + tool
            + " VC/FZ FORMULA: RPM "
            + parameters.rpm()
            + ", F "
            + parameters.feedMmPerMinute()
            + " MM/MIN, FZ "
            + f(chipLoad)
            + " MM)");
  }

  private int rpm(int surfaceSpeed, double diameterMm, int maximum) {
    int calculated = (int) Math.round((surfaceSpeed * 1000.0) / (Math.PI * diameterMm));
    return Math.max(500, Math.min(calculated, maximum));
  }

  private String dimensions(Dimensions dimensions) {
    return f(dimensions.xMm()) + " X " + f(dimensions.yMm()) + " X " + f(dimensions.zMm());
  }

  private String f(double value) {
    return String.format(Locale.ROOT, "%.3f", value);
  }

  private record CuttingParameters(int rpm, int feedMmPerMinute) {}

  public record GeneratedProgram(String gcode, ProgramManifest manifest) {}
}
