package com.notso.tinyurl.infrastructure.config;

import com.notso.tinyurl.application.service.ShortUrlApplicationService;
import com.notso.tinyurl.domain.repository.ShortUrlRepository;
import com.notso.tinyurl.domain.repository.UrlAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanConfiguration {

  private final ApplicationProperties applicationProperties;

  @Bean
  public ShortUrlApplicationService shortUrlApplicationService(
      ShortUrlRepository shortUrlRepository, UrlAccessRepository urlAccessRepository) {
    return new ShortUrlApplicationService(
        shortUrlRepository, urlAccessRepository, applicationProperties.getBaseUrl());
  }
}
