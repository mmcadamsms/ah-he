package com.ahhe.operations.model;

public final class OperationException extends RuntimeException {
  private final int status;
  private final String code;

  public OperationException(int status, String code, String message) {
    super(message);
    this.status = status;
    this.code = code;
  }

  public OperationException(int status, String code, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
    this.code = code;
  }

  public int status() {
    return status;
  }

  public String code() {
    return code;
  }

  public static OperationException conflict(String message) {
    return new OperationException(409, "CONFLICT", message);
  }

  public static OperationException unresolved(String message) {
    return new OperationException(422, "PREREQUISITE_UNRESOLVED", message);
  }

  public static OperationException denied() {
    return new OperationException(403, "FORBIDDEN", "The operation is outside the granted scope.");
  }
}
