package com.sparta.onboarding.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components())
        .components(new Components()
          .addSecuritySchemes("bearer-key",
            new SecurityScheme()
              .type(SecurityScheme.Type.HTTP)
              .scheme("bearer")
              .bearerFormat("JWT")))
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .description("온보딩 프로젝트 API 문서\n\n" +
            "## 주요 기능\n" +
            "- 사용자 관리\n" +
            "- 인증/인가\n")
        .version("1.0.0");
  }
}
