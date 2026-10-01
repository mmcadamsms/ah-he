package com.ahhe.webapi.manufacturing.controller;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import com.ahhe.webapi.manufacturing.service.ManufacturingJobService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/v1/manufacturing-jobs")
public class ManufacturingJobController {

  private final ManufacturingJobService service;

  public ManufacturingJobController(ManufacturingJobService service) {
    this.service = service;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<ManufacturingJob>> submit(
      @RequestParam("file") MultipartFile file,
      @RequestParam("material") String material,
      @RequestParam(name = "simulationSpeed", defaultValue = "5") double simulationSpeed) {
    ManufacturingJob job = service.submit(file, material, simulationSpeed);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.of(job));
  }

  @PatchMapping(
      value = "/{id}/simulation-speed",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse<ManufacturingJob> updateSimulationSpeed(
      @PathVariable UUID id, @RequestBody SimulationSpeedRequest request) {
    return ApiResponse.of(service.updateSimulationSpeed(id, request.speed()));
  }

  @PostMapping("/{id}/resume")
  public ApiResponse<ManufacturingJob> resume(@PathVariable UUID id) {
    return ApiResponse.of(service.resume(id));
  }

  @GetMapping("/{id}")
  public ApiResponse<ManufacturingJob> get(@PathVariable UUID id) {
    return ApiResponse.of(service.get(id));
  }

  @GetMapping
  public ApiResponse<List<ManufacturingJob>> list() {
    return ApiResponse.of(service.list());
  }

  @GetMapping(value = "/{id}/gcode", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> gcode(@PathVariable UUID id) {
    ManufacturingJob job = service.get(id);
    if (job.getGcode() == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("G-code has not been generated for this job.");
    }
    return ResponseEntity.ok(job.getGcode());
  }

  public record ApiResponse<T>(T data, Meta meta) {

    public static <T> ApiResponse<T> of(T data) {
      return new ApiResponse<>(data, new Meta(Instant.now(), UUID.randomUUID()));
    }
  }

  public record Meta(Instant timestamp, UUID requestId) {}

  public record SimulationSpeedRequest(double speed) {}
}
