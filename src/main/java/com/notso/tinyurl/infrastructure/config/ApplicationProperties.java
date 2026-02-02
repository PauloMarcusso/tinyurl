package com.notso.tinyurl.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

  private String baseUrl = "http://localhost:8080";
  private int shortCodeLength = 8;
  private int defaultExpirationDays = 0;
}
