package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.GeometryAnalysis;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeometryAnalyzer {

  private static final Pattern STEP_POINT =
      Pattern.compile(
          "CARTESIAN_POINT\\s*\\([^,]*,\\s*\\(\\s*([-+0-9.Ee]+)\\s*,\\s*"
              + "([-+0-9.Ee]+)\\s*,\\s*([-+0-9.Ee]+)\\s*\\)\\s*\\)",
          Pattern.CASE_INSENSITIVE);
  private static final Pattern STL_VERTEX =
      Pattern.compile(
          "\\bvertex\\s+([-+0-9.Ee]+)\\s+([-+0-9.Ee]+)\\s+([-+0-9.Ee]+)",
          Pattern.CASE_INSENSITIVE);
  private static final int MAX_POINTS = 1_000_000;
  private final GeometryKernelClient geometryKernelClient;

  @Autowired
  public GeometryAnalyzer(GeometryKernelClient geometryKernelClient) {
    this.geometryKernelClient = geometryKernelClient;
  }

  GeometryAnalyzer() {
    this(GeometryKernelClient.disabled());
  }

  public GeometryAnalysis analyze(String fileName, byte[] content) {
    if (content.length == 0) {
      throw new IllegalArgumentException("The uploaded model is empty.");
    }

    String extension = extension(fileName);
    return switch (extension) {
      case "step", "stp" -> analyzeStep(fileName, content);
      case "stl" -> analyzeStl(content);
      case "sldprt", "f3d" ->
          throw new IllegalArgumentException(
              "Native SolidWorks and Fusion 360 files are not translated by this prototype. "
                  + "Export the part as STEP or STL and upload the export.");
      default ->
          throw new IllegalArgumentException(
              "Unsupported file type. Upload an exported .step, .stp, or .stl model.");
    };
  }

  private GeometryAnalysis analyzeStep(String fileName, byte[] content) {
    if (geometryKernelClient.isEnabled()) {
      GeometryKernelClient.KernelAnalysis analysis =
          geometryKernelClient.analyze(fileName, content);
      List<String> warnings = new ArrayList<>(analysis.warnings());
      warnings.add(
          "Exact topology came from "
              + analysis.kernel()
              + "; setup and tool-access optimization are evaluated separately.");
      return new GeometryAnalysis(
          "STEP",
          analysis.dimensions(),
          analysis.faces().size(),
          analysis.solidCount(),
          analysis.volumeMm3(),
          analysis.faces(),
          warnings);
    }
    String text = new String(content, StandardCharsets.ISO_8859_1);
    if (!text.startsWith("ISO-10303-21")) {
      throw new IllegalArgumentException("The uploaded file is not a valid STEP exchange file.");
    }

    Bounds bounds = new Bounds();
    Matcher matcher = STEP_POINT.matcher(text);
    while (matcher.find() && bounds.count < MAX_POINTS) {
      bounds.accept(
          parse(matcher.group(1)), parse(matcher.group(2)), parse(matcher.group(3)));
    }
    bounds.validate();

    List<String> warnings = new ArrayList<>();
    warnings.add(
        "Bounding dimensions are estimated from STEP Cartesian points; exact feature recognition "
            + "requires a CAD kernel.");
    if (!text.contains(".MILLI.")) {
      warnings.add("STEP units could not be proven to be millimeters; verify model units.");
    }
    return new GeometryAnalysis("STEP", bounds.dimensions(), bounds.count, warnings);
  }

  private GeometryAnalysis analyzeStl(byte[] content) {
    Bounds bounds =
        isBinaryStl(content) ? analyzeBinaryStl(content) : analyzeAsciiStl(content);
    bounds.validate();
    return new GeometryAnalysis(
        "STL",
        bounds.dimensions(),
        bounds.count,
        List.of(
            "STL has no authoritative unit metadata; this prototype interprets coordinates as "
                + "millimeters.",
            "Mesh dimensions do not provide tolerances, datums, threads, or manufacturing intent."));
  }

  private Bounds analyzeAsciiStl(byte[] content) {
    String text = new String(content, StandardCharsets.US_ASCII);
    Bounds bounds = new Bounds();
    Matcher matcher = STL_VERTEX.matcher(text);
    while (matcher.find() && bounds.count < MAX_POINTS) {
      bounds.accept(
          parse(matcher.group(1)), parse(matcher.group(2)), parse(matcher.group(3)));
    }
    return bounds;
  }

  private Bounds analyzeBinaryStl(byte[] content) {
    ByteBuffer buffer = ByteBuffer.wrap(content).order(ByteOrder.LITTLE_ENDIAN);
    long triangles = Integer.toUnsignedLong(buffer.getInt(80));
    if (triangles > 5_000_000 || 84L + triangles * 50L > content.length) {
      throw new IllegalArgumentException("The binary STL triangle table is invalid.");
    }

    Bounds bounds = new Bounds();
    buffer.position(84);
    for (long triangle = 0; triangle < triangles && bounds.count < MAX_POINTS; triangle++) {
      buffer.position(buffer.position() + 12);
      for (int vertex = 0; vertex < 3; vertex++) {
        bounds.accept(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
      }
      buffer.position(buffer.position() + 2);
    }
    return bounds;
  }

  private boolean isBinaryStl(byte[] content) {
    if (content.length < 84) {
      return false;
    }
    ByteBuffer buffer = ByteBuffer.wrap(content, 80, 4).order(ByteOrder.LITTLE_ENDIAN);
    long triangles = Integer.toUnsignedLong(buffer.getInt());
    return 84L + triangles * 50L == content.length;
  }

  private String extension(String fileName) {
    int separator = fileName.lastIndexOf('.');
    return separator < 0 ? "" : fileName.substring(separator + 1).toLowerCase(Locale.ROOT);
  }

  private double parse(String value) {
    return Double.parseDouble(value);
  }

  private static final class Bounds {
    private double minX = Double.POSITIVE_INFINITY;
    private double minY = Double.POSITIVE_INFINITY;
    private double minZ = Double.POSITIVE_INFINITY;
    private double maxX = Double.NEGATIVE_INFINITY;
    private double maxY = Double.NEGATIVE_INFINITY;
    private double maxZ = Double.NEGATIVE_INFINITY;
    private int count;

    private void accept(double x, double y, double z) {
      if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
        return;
      }
      minX = Math.min(minX, x);
      minY = Math.min(minY, y);
      minZ = Math.min(minZ, z);
      maxX = Math.max(maxX, x);
      maxY = Math.max(maxY, y);
      maxZ = Math.max(maxZ, z);
      count++;
    }

    private void validate() {
      if (count < 3) {
        throw new IllegalArgumentException("No usable 3D geometry points were found.");
      }
      Dimensions dimensions = dimensions();
      if (dimensions.xMm() <= 0 || dimensions.yMm() <= 0 || dimensions.zMm() <= 0) {
        throw new IllegalArgumentException("The model does not have a non-zero 3D bounding box.");
      }
    }

    private Dimensions dimensions() {
      return new Dimensions(round(maxX - minX), round(maxY - minY), round(maxZ - minZ));
    }

    private double round(double value) {
      return Math.round(value * 100.0) / 100.0;
    }
  }
}
