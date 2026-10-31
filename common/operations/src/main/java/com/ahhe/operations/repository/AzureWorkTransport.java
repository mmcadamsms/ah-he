package com.ahhe.operations.repository;

import com.ahhe.operations.model.Checks;
import com.ahhe.operations.model.OperationException;
import com.azure.core.util.Context;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.models.QueueStorageException;
import java.time.Duration;
import java.util.Optional;

public final class AzureWorkTransport implements WorkTransport {
  private final QueueClient client;
  private final JsonCodec codec = new JsonCodec();

  public AzureWorkTransport(QueueClient client) {
    this.client = client;
  }

  @Override
  public void send(String workId) {
    try {
      client.sendMessageWithResponse(
          codec.encode(new Pointer(1, Checks.id(workId))),
          Duration.ZERO,
          Duration.ofDays(7),
          Duration.ofSeconds(30),
          Context.NONE);
    } catch (QueueStorageException exception) {
      throw unavailable(exception);
    }
  }

  @Override
  public Optional<Delivery> receive() {
    try {
      var messages =
          client
              .receiveMessages(1, Duration.ofSeconds(60), Duration.ofSeconds(30), Context.NONE)
              .iterator();
      if (!messages.hasNext()) {
        return Optional.empty();
      }
      var message = messages.next();
      var pointer = codec.decode(message.getMessageText(), Pointer.class);
      return Optional.of(
          new Delivery(message.getMessageId(), message.getPopReceipt(), pointer.workId()));
    } catch (QueueStorageException exception) {
      throw unavailable(exception);
    }
  }

  @Override
  public void acknowledge(Delivery delivery) {
    try {
      client.deleteMessage(delivery.id(), delivery.receipt());
    } catch (QueueStorageException exception) {
      throw unavailable(exception);
    }
  }

  private OperationException unavailable(QueueStorageException exception) {
    return new OperationException(
        503,
        "WORK_TRANSPORT_UNAVAILABLE",
        "Work notification transport is unavailable.",
        exception);
  }
}
