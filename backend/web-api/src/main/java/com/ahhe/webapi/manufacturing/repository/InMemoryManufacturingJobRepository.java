package com.ahhe.webapi.manufacturing.repository;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryManufacturingJobRepository implements ManufacturingJobRepository {

  private final ConcurrentHashMap<UUID, ManufacturingJob> jobs = new ConcurrentHashMap<>();

  @Override
  public ManufacturingJob save(ManufacturingJob job) {
    jobs.put(job.getId(), job);
    return job;
  }

  @Override
  public Optional<ManufacturingJob> findById(UUID id) {
    return Optional.ofNullable(jobs.get(id));
  }

  @Override
  public List<ManufacturingJob> findAll() {
    return jobs.values().stream()
        .sorted(Comparator.comparing(ManufacturingJob::getCreatedAt).reversed())
        .toList();
  }
}

