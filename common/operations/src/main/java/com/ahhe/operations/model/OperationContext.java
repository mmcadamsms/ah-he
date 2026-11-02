package com.ahhe.operations.model;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record OperationContext(
    String businessId,
    String principalId,
    String representedCustomerId,
    Beneficiary primaryBeneficiary,
    List<Beneficiary> additionalBeneficiaries,
    String purpose,
    String idempotencyKey,
    String causedByOperationId) {
  public OperationContext {
    Checks.id(businessId);
    Checks.id(principalId);
    representedCustomerId = representedCustomerId == null ? "" : representedCustomerId;
    if (!representedCustomerId.isEmpty()) {
      Checks.id(representedCustomerId);
    }
    Checks.required(primaryBeneficiary);
    Checks.bounded(additionalBeneficiaries, 8);
    additionalBeneficiaries = List.copyOf(additionalBeneficiaries);
    Checks.text(purpose, 500);
    Checks.id(idempotencyKey);
    causedByOperationId = causedByOperationId == null ? "" : causedByOperationId;
    if (!causedByOperationId.isEmpty()) {
      Checks.id(causedByOperationId);
    }
  }

  public enum BeneficiaryKind {
    BUSINESS,
    CUSTOMER,
    ORDER_LINE
  }

  public enum PrincipalKind {
    HUMAN,
    SERVICE,
    DEVICE
  }

  public enum Role {
    ADMIN,
    INVENTORY,
    MAINTENANCE,
    QUALITY,
    COMMERCIAL,
    RETURNS,
    CUSTOMER,
    CUSTOMER_ADMIN,
    WORKER
  }

  public record Beneficiary(BeneficiaryKind kind, String id) {
    public Beneficiary {
      Checks.required(kind);
      Checks.id(id);
    }
  }

  public record Grant(
      String id,
      PrincipalKind kind,
      String customerId,
      Set<Role> roles,
      boolean active,
      Instant expiresAt) {
    public Grant {
      Checks.id(id);
      Checks.required(kind);
      customerId = customerId == null ? "" : customerId;
      if (!customerId.isEmpty()) {
        Checks.id(customerId);
      }
      Checks.bounded(roles, 16);
      roles = java.util.Collections.unmodifiableSet(new java.util.TreeSet<>(roles));
      Checks.required(expiresAt);
    }

    public boolean permits(Role role, Instant now) {
      return active
          && now.isBefore(expiresAt)
          && (roles.contains(role)
              || (role == Role.CUSTOMER && roles.contains(Role.CUSTOMER_ADMIN)));
    }
  }

  public record Outcome(String operationId, List<String> subjects, Instant committedAt) {}

  public record Audit(
      String operationId,
      String action,
      OperationContext context,
      Grant actor,
      Instant recordedAt,
      List<String> subjects) {}
}
