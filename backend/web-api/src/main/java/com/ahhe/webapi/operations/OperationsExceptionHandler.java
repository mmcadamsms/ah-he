package com.ahhe.webapi.operations;

import com.ahhe.operations.model.OperationException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice(assignableTypes = OperationsController.class)
public class OperationsExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(OperationsExceptionHandler.class);

  public record Error(String code, String message, List<String> details) {}

  public record Failure(Error error, OperationsController.Meta meta) {}

  @ExceptionHandler(OperationException.class)
  public ResponseEntity<Failure> domain(OperationException exception) {
    var id = UUID.randomUUID();
    LOG.warn("event=operation.failed code={} requestId={}", exception.code(), id);
    return ResponseEntity.status(exception.status())
        .body(
            new Failure(
                new Error(exception.code(), exception.getMessage(), List.of()),
                new OperationsController.Meta(Instant.now(), id)));
  }

  @ExceptionHandler({
    IllegalArgumentException.class,
    HttpMessageNotReadableException.class,
    MissingRequestHeaderException.class
  })
  public ResponseEntity<Failure> input(Exception exception) {
    return ResponseEntity.badRequest()
        .body(
            new Failure(
                new Error(
                    "VALIDATION_ERROR",
                    "Invalid command fields, context or idempotency header.",
                    List.of()),
                new OperationsController.Meta(Instant.now(), UUID.randomUUID())));
  }
}
