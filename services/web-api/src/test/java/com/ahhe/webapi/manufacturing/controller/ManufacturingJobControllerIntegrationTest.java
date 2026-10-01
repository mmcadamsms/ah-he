package com.ahhe.webapi.manufacturing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ManufacturingJobControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void submitsAndCompletesManufacturingJob() throws Exception {
    String stl =
        """
        solid block
          facet normal 0 0 1
            outer loop
              vertex 0 0 0
              vertex 80 0 0
              vertex 0 40 12
            endloop
          endfacet
        endsolid block
        """;
    MockMultipartFile file =
        new MockMultipartFile(
            "file", "sample.stl", "model/stl", stl.getBytes(StandardCharsets.US_ASCII));

    String response =
        mockMvc
            .perform(multipart("/api/v1/manufacturing-jobs").file(file).param("material", "steel"))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.data.id").exists())
            .andReturn()
            .getResponse()
            .getContentAsString();

    String id = objectMapper.readTree(response).path("data").path("id").asText();
    JsonNode job = waitForTerminalState(id);

    assertThat(job.path("status").asText()).isEqualTo("COMPLETED");
    assertThat(job.path("stock").path("selectedGrade").asText())
        .isEqualTo("AISI 1018 cold-finished steel");
    assertThat(job.path("tools").size()).isEqualTo(6);
    assertThat(job.path("gcode").asText()).contains("O1001");
    assertThat(job.path("programManifest").path("physicalSetups").asInt()).isEqualTo(1);
    assertThat(job.path("programManifest").path("indexedOrientations").asInt()).isEqualTo(3);
    assertThat(job.path("machine").path("estimatedTotalSeconds").asDouble()).isGreaterThan(45);
  }

  private JsonNode waitForTerminalState(String id) throws Exception {
    JsonNode job = null;
    for (int attempt = 0; attempt < 50; attempt++) {
      String response =
          mockMvc
              .perform(get("/api/v1/manufacturing-jobs/{id}", id))
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      job = objectMapper.readTree(response).path("data");
      if (job.path("status").asText().matches("COMPLETED|FAILED")) {
        return job;
      }
      Thread.sleep(20);
    }
    return job;
  }
}
