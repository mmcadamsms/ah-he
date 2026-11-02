package com.ahhe.operations.repository;

import com.ahhe.operations.model.OperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;

public final class JsonCodec {
  private final ObjectMapper mapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .registerModule(
              new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
          .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
          .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public String encode(Object value) {
    try {
      return mapper.writeValueAsString(value);
    } catch (JsonProcessingException exception) {
      throw new OperationException(
          500, "OPERATIONAL_ENCODING_FAILED", "Cannot encode an operational record.", exception);
    }
  }

  public <T> T decode(String json, Class<T> type) {
    try {
      return mapper.readValue(json, type);
    } catch (JsonProcessingException exception) {
      throw new OperationException(
          500,
          "OPERATIONAL_SCHEMA_INVALID",
          "Persisted schema requires reconciliation.",
          exception);
    }
  }
}
