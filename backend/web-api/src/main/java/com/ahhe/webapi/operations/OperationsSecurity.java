package com.ahhe.webapi.operations;

import com.ahhe.operations.model.Checks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OperationsSecurity {
  @Bean
  @Order(1)
  public SecurityFilterChain operationalApi(
      HttpSecurity http, @Value("${operations.enabled:false}") boolean enabled) throws Exception {
    http.securityMatcher("/api/v1/operations/**")
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    if (enabled) {
      http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
          .oauth2ResourceServer(server -> server.jwt(jwt -> {}));
    } else {
      http.authorizeHttpRequests(auth -> auth.anyRequest().denyAll());
    }
    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain prototypeCompatibility(
      HttpSecurity http, @Value("${operations.enabled:false}") boolean enabled) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authorizeHttpRequests(
        auth -> {
          if (enabled) {
            auth.requestMatchers("/api/**").denyAll();
          }
          auth.anyRequest().permitAll();
        });
    return http.build();
  }

  @Bean
  @ConditionalOnProperty(name = "operations.enabled", havingValue = "true")
  @ConditionalOnMissingBean(JwtDecoder.class)
  public JwtDecoder operationalJwtDecoder(
      @Value("${operations.jwt.issuer:}") String issuer,
      @Value("${operations.jwt.jwk-set-uri:}") String keys,
      @Value("${operations.jwt.audience:}") String audience) {
    Checks.text(issuer, 1000);
    Checks.text(keys, 1000);
    Checks.text(audience, 300);
    if (!issuer.startsWith("https://") || !keys.startsWith("https://")) {
      throw new IllegalArgumentException("OIDC issuer and JWKS require HTTPS.");
    }
    var decoder = NimbusJwtDecoder.withJwkSetUri(keys).build();
    OAuth2TokenValidator<Jwt> audienceValidator =
        jwt ->
            jwt.getAudience().contains(audience)
                ? OAuth2TokenValidatorResult.success()
                : OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "Token audience does not match.", null));
    decoder.setJwtValidator(
        new DelegatingOAuth2TokenValidator<>(
            JwtValidators.createDefaultWithIssuer(issuer), audienceValidator));
    return decoder;
  }
}
