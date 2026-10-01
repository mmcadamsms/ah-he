package com.ahhe.webapi.manufacturing.controller;

import com.ahhe.webapi.manufacturing.service.JobNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(JobNotFoundException.class)
  public ResponseEntity<ApiError> notFound(JobNotFoundException exception) {
    return error(HttpStatus.NOT_FOUND, "NOT_FOUND", exception.getMessage());
  }

  @ExceptionHandler({
    IllegalArgumentException.class,
    MissingServletRequestParameterException.class,
    MethodArgumentNotValidException.class,
    MaxUploadSizeExceededException.class
  })
  public ResponseEntity<ApiError> validation(Exception exception) {
    String message =
        exception instanceof MaxUploadSizeExceededException
            ? "The uploaded model exceeds the 10 MB prototype limit."
            : exception.getMessage();
    return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message);
  }

  private ResponseEntity<ApiError> error(HttpStatus status, String code, String message) {
    return ResponseEntity.status(status)
        .body(
            new ApiError(
                new ErrorBody(code, message, List.of()),
                new Meta(Instant.now(), UUID.randomUUID())));
  }

  public record ApiError(ErrorBody error, Meta meta) {}

  public record ErrorBody(String code, String message, List<String> details) {}

  public record Meta(Instant timestamp, UUID requestId) {}
}

