package com.ahhe.operations.repository;

import java.util.Optional;

public interface WorkTransport {
  record Pointer(int version, String workId) {
    public Pointer {
      if (version != 1) {
        throw new com.ahhe.operations.model.OperationException(
            500,
            "WORK_PROTOCOL_UNSUPPORTED",
            "Work reference requires a supported protocol version.");
      }
      com.ahhe.operations.model.Checks.id(workId);
    }
  }

  record Delivery(String id, String receipt, String workId) {}

  void send(String workId);

  Optional<Delivery> receive();

  void acknowledge(Delivery delivery);
}
