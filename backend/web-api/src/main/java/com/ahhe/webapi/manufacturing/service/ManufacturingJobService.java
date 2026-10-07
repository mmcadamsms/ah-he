package com.ahhe.webapi.manufacturing.service;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import com.ahhe.webapi.manufacturing.repository.ManufacturingJobRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ManufacturingJobService {

  private final ManufacturingJobRepository repository;
  private final ManufacturingOrchestrator orchestrator;

  public ManufacturingJobService(
      ManufacturingJobRepository repository, ManufacturingOrchestrator orchestrator) {
    this.repository = repository;
    this.orchestrator = orchestrator;
  }

  public ManufacturingJob submit(MultipartFile file, String material, double simulationSpeed) {
    if (file.isEmpty() || file.getOriginalFilename() == null) {
      throw new IllegalArgumentException("Select a non-empty CAD export.");
    }
    if (material == null || material.isBlank()) {
      throw new IllegalArgumentException("Select or enter a material.");
    }

    try {
      byte[] content = file.getBytes();
      ManufacturingJob job =
          new ManufacturingJob(
              UUID.randomUUID(),
              sanitize(file.getOriginalFilename()),
              material.trim(),
              simulationSpeed);
      repository.save(job);
      orchestrator.process(job, content);
      return job;
    } catch (java.io.IOException exception) {
      throw new IllegalArgumentException("The uploaded model could not be read.", exception);
    }
  }

  public ManufacturingJob get(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new JobNotFoundException("Manufacturing job was not found."));
  }

  public List<ManufacturingJob> list() {
    return repository.findAll();
  }

  public ManufacturingJob updateSimulationSpeed(UUID id, double simulationSpeed) {
    ManufacturingJob job = get(id);
    job.setSimulationSpeed(simulationSpeed);
    return repository.save(job);
  }

  public ManufacturingJob resume(UUID id) {
    ManufacturingJob job = get(id);
    job.resumeFromOperatorStop();
    return repository.save(job);
  }

  private String sanitize(String fileName) {
    String normalized = fileName.replace('\\', '/');
    return normalized.substring(normalized.lastIndexOf('/') + 1);
  }
}
