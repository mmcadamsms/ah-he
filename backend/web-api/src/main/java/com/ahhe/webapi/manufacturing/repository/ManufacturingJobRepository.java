package com.ahhe.webapi.manufacturing.repository;

import com.ahhe.webapi.manufacturing.model.ManufacturingJob;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManufacturingJobRepository {

  ManufacturingJob save(ManufacturingJob job);

  Optional<ManufacturingJob> findById(UUID id);

  List<ManufacturingJob> findAll();
}

