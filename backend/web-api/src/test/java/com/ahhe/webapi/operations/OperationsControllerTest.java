package com.ahhe.webapi.operations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ahhe.operations.model.OperationContext;
import com.ahhe.operations.model.OperationException;
import com.ahhe.operations.service.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    value = OperationsController.class,
    properties = {"operations.enabled=true", "operations.business-id=shop"})
@Import({
  OperationsSecurity.class,
  OperationsJsonConfiguration.class,
  OperationsExceptionHandler.class
})
class OperationsControllerTest {
  @Autowired private MockMvc mvc;
  @MockBean private JwtDecoder decoder;
  @MockBean private OperationEngine engine;
  @MockBean private InventoryService inventory;
  @MockBean private CareService care;
  @MockBean private InspectionService inspection;
  @MockBean private CommercialService commercial;
  @MockBean private ReturnService returns;
  @MockBean private EvidenceService evidence;
  private static final String ISSUER = "https://issuer.example";

  @Test
  void operationsRequireAuthenticationAndNeverUseThePrototypeAsAnAuthFallback() throws Exception {
    mvc.perform(get("/api/v1/operations/me")).andExpect(status().isUnauthorized());
    mvc.perform(get("/api/v1/manufacturing-jobs")).andExpect(status().isForbidden());
  }

  @Test
  void actorIsDerivedFromVerifiedJwtAndDomainFailuresStayExplicit() throws Exception {
    var outcome =
        new OperationContext.Outcome("operation", List.of("H!inventory!item"), Instant.now());
    when(inventory.receive(any(), any())).thenReturn(outcome);
    var body =
        """
{"context":{"representedCustomerId":"","primaryBeneficiary":{"kind":"BUSINESS","id":"shop"},
"additionalBeneficiaries":[],"purpose":"Controlled receipt"},
"input":{"id":"item","customerId":"","owner":"shop","category":"stock","materialLot":"lot",
"quantity":"10","reserved":"0","unit":"pieces","location":"receiving","custodian":"operator",
"condition":"QUARANTINED","parents":[]}}
""";
    mvc.perform(
            post("/api/v1/operations/inventory-items")
                .with(jwt().jwt(token -> token.claim("iss", ISSUER).subject("member")))
                .header("Idempotency-Key", "command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.operationId").value("operation"));
    var context = ArgumentCaptor.forClass(OperationContext.class);
    verify(inventory).receive(context.capture(), any());
    org.assertj.core.api.Assertions.assertThat(context.getValue().principalId())
        .isEqualTo(OperationEngine.principalId(ISSUER, "member"));
    when(inventory.receive(any(), any()))
        .thenThrow(OperationException.unresolved("Stock is held."));
    mvc.perform(
            post("/api/v1/operations/inventory-items")
                .with(jwt().jwt(token -> token.claim("iss", ISSUER).subject("member")))
                .header("Idempotency-Key", "command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.error.code").value("PREREQUISITE_UNRESOLVED"));
  }

  @Test
  void unknownOrForgedContextFieldsAndMissingCommandKeysAreRejected() throws Exception {
    var body =
        """
{"context":{"principalId":"admin","representedCustomerId":"",
"primaryBeneficiary":{"kind":"BUSINESS","id":"shop"},"additionalBeneficiaries":[],"purpose":"Receipt"},
"input":{}}
""";
    mvc.perform(
            post("/api/v1/operations/inventory-items")
                .with(jwt().jwt(token -> token.claim("iss", ISSUER).subject("member")))
                .header("Idempotency-Key", "command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isBadRequest());
    verifyNoInteractions(inventory);
    mvc.perform(
            post("/api/v1/operations/inventory-items")
                .with(jwt().jwt(token -> token.claim("iss", ISSUER).subject("member")))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void verifiedAccountContextIncludesTheOperatingBusinessNotClientTenantClaims() throws Exception {
    when(engine.actorGrant("shop", OperationEngine.principalId(ISSUER, "member")))
        .thenReturn(
            new OperationContext.Grant(
                "member",
                OperationContext.PrincipalKind.HUMAN,
                "customerA",
                Set.of(OperationContext.Role.CUSTOMER),
                true,
                Instant.now().plusSeconds(100)));
    mvc.perform(
            get("/api/v1/operations/me")
                .with(
                    jwt()
                        .jwt(
                            token ->
                                token
                                    .claim("iss", ISSUER)
                                    .subject("member")
                                    .claim("businessId", "other"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.businessId").value("shop"))
        .andExpect(jsonPath("$.data.grant.customerId").value("customerA"));
  }
}
