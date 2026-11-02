package com.ahhe.operations.model;

import java.math.BigDecimal;
import java.util.Collection;

public final class Checks {
  private Checks() {}

  public static String id(String value) {
    if (value == null || !value.matches("[A-Za-z0-9_-]{1,100}")) {
      throw new IllegalArgumentException("Identifiers must contain 1-100 letters, digits, _ or -.");
    }
    return value;
  }

  public static String text(String value, int maximum) {
    if (value == null || value.isBlank() || value.length() > maximum) {
      throw new IllegalArgumentException(
          "A nonblank value within " + maximum + " characters is required.");
    }
    return value;
  }

  public static BigDecimal positive(BigDecimal value) {
    if (value == null
        || value.signum() <= 0
        || value.precision() > 28
        || value.scale() < -12
        || value.scale() > 12) {
      throw new IllegalArgumentException("A positive bounded decimal quantity is required.");
    }
    return value;
  }

  public static BigDecimal nonnegative(BigDecimal value) {
    if (value == null
        || value.signum() < 0
        || value.precision() > 28
        || value.scale() < -12
        || value.scale() > 12) {
      throw new IllegalArgumentException("A nonnegative bounded decimal is required.");
    }
    return value;
  }

  public static <T> T required(T value) {
    if (value == null) {
      throw new IllegalArgumentException("A required field is missing.");
    }
    return value;
  }

  public static BigDecimal decimal(BigDecimal value) {
    if (value == null || value.precision() > 28 || value.scale() < -12 || value.scale() > 12) {
      throw new IllegalArgumentException("A bounded decimal value is required.");
    }
    return value;
  }

  public static <T> void bounded(Collection<T> values, int maximum) {
    required(values);
    if (values.size() > maximum) {
      throw new IllegalArgumentException("The collection exceeds its limit of " + maximum + ".");
    }
  }
}
