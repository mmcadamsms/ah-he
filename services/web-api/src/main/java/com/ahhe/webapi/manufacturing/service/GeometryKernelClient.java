package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Dimensions;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.CylindricalSurface;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.FaceAnalysis;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.IndexedOrientation;
import com.ahhe.webapi.manufacturing.model.ManufacturingJob.Vector3;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeometryKernelClient {

  private final String baseUrl;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  @Autowired
  public GeometryKernelClient(
      @Value("${manufacturing.geometry-worker-url:${GEOMETRY_WORKER_URL:}}") String baseUrl,
      ObjectMapper objectMapper) {
    this(
        baseUrl,
        HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build(),
        objectMapper);
  }

  GeometryKernelClient(String baseUrl, HttpClient httpClient, ObjectMapper objectMapper) {
    this.baseUrl = baseUrl == null ? "" : baseUrl.strip();
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
  }

  static GeometryKernelClient disabled() {
    return new GeometryKernelClient("", HttpClient.newHttpClient(), new ObjectMapper());
  }

  public boolean isEnabled() {
    return !baseUrl.isBlank();
  }

  public KernelAnalysis analyze(String fileName, byte[] content) {
    if (!isEnabled()) {
      throw new IllegalStateException("The geometry kernel worker is not configured.");
    }
    String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(
                URI.create(
                    baseUrl + "/api/v1/geometry/analyze?filename=" + encodedName))
            .timeout(Duration.ofSeconds(60))
            .header("Content-Type", "application/octet-stream")
            .POST(HttpRequest.BodyPublishers.ofByteArray(content))
            .build();
    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
      JsonNode payload = objectMapper.readTree(response.body());
      if (response.statusCode() != 200) {
        throw new IllegalArgumentException(
            "Geometry kernel rejected the model: "
                + payload.path("message").asText(payload.path("error").asText()));
      }
      return map(payload);
    } catch (IOException exception) {
      throw new IllegalStateException("Geometry kernel response could not be read.", exception);
    } catch (InterruptedException interrupted) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Geometry kernel request was interrupted.", interrupted);
    }
  }

  private KernelAnalysis map(JsonNode payload) {
    JsonNode bounds = payload.path("boundsMm");
    List<FaceAnalysis> faces = new ArrayList<>();
    for (JsonNode face : payload.path("faces")) {
      JsonNode center = face.path("centerMm");
      JsonNode normal = face.path("normal");
      JsonNode orientation = face.path("candidateOrientation");
      JsonNode cylinder = face.path("cylinder");
      faces.add(
          new FaceAnalysis(
              face.path("id").asText(),
              face.path("surfaceType").asText(),
              face.path("areaMm2").asDouble(),
              vector(center),
              vector(normal),
              face.path("edgeCount").asInt(),
              orientation.isObject()
                  ? new IndexedOrientation(
                      orientation.path("bDegrees").asDouble(),
                      orientation.path("cDegrees").asDouble())
                  : null,
              cylinder.isObject()
                  ? new CylindricalSurface(
                      cylinder.path("radiusMm").asDouble(),
                      vector(cylinder.path("axisDirection")),
                      vector(cylinder.path("axisPointMm")),
                      cylinder.path("internal").asBoolean())
                  : null));
    }
    List<String> warnings = new ArrayList<>();
    payload.path("warnings").forEach(warning -> warnings.add(warning.asText()));
    return new KernelAnalysis(
        payload.path("kernel").asText(),
        new Dimensions(
            bounds.path("x").asDouble(),
            bounds.path("y").asDouble(),
            bounds.path("z").asDouble()),
        payload.path("solidCount").asInt(),
        payload.path("volumeMm3").asDouble(),
        List.copyOf(faces),
        List.copyOf(warnings));
  }

  private Vector3 vector(JsonNode values) {
    return new Vector3(values.path(0).asDouble(), values.path(1).asDouble(), values.path(2).asDouble());
  }

  public record KernelAnalysis(
      String kernel,
      Dimensions dimensions,
      int solidCount,
      double volumeMm3,
      List<FaceAnalysis> faces,
      List<String> warnings) {}
}
