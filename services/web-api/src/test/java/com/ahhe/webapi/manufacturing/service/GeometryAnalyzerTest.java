package com.ahhe.webapi.manufacturing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class GeometryAnalyzerTest {

  private final GeometryAnalyzer analyzer = new GeometryAnalyzer();

  @Test
  void analyzesStepBoundingBox() {
    String step =
        """
        ISO-10303-21;
        HEADER;
        ENDSEC;
        DATA;
        #1=CARTESIAN_POINT('',(0.,0.,0.));
        #2=CARTESIAN_POINT('',(80.,40.,12.));
        #3=CARTESIAN_POINT('',(-5.,10.,2.));
        ENDSEC;
        END-ISO-10303-21;
        """;

    var result = analyzer.analyze("bracket.step", step.getBytes(StandardCharsets.US_ASCII));

    assertThat(result.format()).isEqualTo("STEP");
    assertThat(result.dimensions().xMm()).isEqualTo(85);
    assertThat(result.dimensions().yMm()).isEqualTo(40);
    assertThat(result.dimensions().zMm()).isEqualTo(12);
    assertThat(result.pointsAnalyzed()).isEqualTo(3);
  }

  @Test
  void rejectsNativeCadWithExportGuidance() {
    assertThatThrownBy(() -> analyzer.analyze("part.sldprt", new byte[] {1}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Export the part as STEP or STL");
  }
}

