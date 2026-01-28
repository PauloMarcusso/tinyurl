package com.notso.tinyurl.application.service;

import com.notso.tinyurl.application.dto.request.CreateShortUrlRequest;
import com.notso.tinyurl.application.dto.response.ShortUrlResponse;
import com.notso.tinyurl.application.port.in.CreateShortUrlUseCase;
import com.notso.tinyurl.application.port.in.DeleteShortUrlUseCase;
import com.notso.tinyurl.application.port.in.GetShortUrlUseCase;
import com.notso.tinyurl.application.port.in.RedirectShortUrlUseCase;
import com.notso.tinyurl.domain.exception.ShortUrlExpiredException;
import com.notso.tinyurl.domain.exception.ShortUrlNotFoundException;
import com.notso.tinyurl.domain.model.ShortUrl;
import com.notso.tinyurl.domain.model.UrlAccess;
import com.notso.tinyurl.domain.repository.ShortUrlRepository;
import com.notso.tinyurl.domain.repository.UrlAccessRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ShortUrlApplicationService
    implements CreateShortUrlUseCase,
        GetShortUrlUseCase,
        RedirectShortUrlUseCase,
        DeleteShortUrlUseCase {

  private final ShortUrlRepository shortUrlRepository;
  private final UrlAccessRepository urlAccessRepository;
  private final String baseUrl;

  @Override
  public ShortUrlResponse execute(CreateShortUrlRequest request) {
    log.info("Creating short URL for {}", request.getUrl());

    ShortUrl shortUrl = ShortUrl.create(request.getUrl(), request.getExpiresAt());

    shortUrl = ensureUniqueShortCode(shortUrl);

    ShortUrl saved = shortUrlRepository.save(shortUrl);

    log.info("Short URL created: {} -> {}", saved.getShortCodeValue(), maskUrl(request.getUrl()));

    return ShortUrlResponse.fromDomain(saved, baseUrl);
  }

  @Override
  public String execute(String shortCode, String ipAddress, String userAgent, String referer) {
    log.debug("Redirecting short URL code: {}", shortCode);

    ShortUrl shortUrl =
        shortUrlRepository
            .findByShortCode(shortCode)
            .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));

    if (shortUrl.isExpired()) {
      log.warn("Attempt to access URL expired: {}", shortCode);
      throw new ShortUrlExpiredException(shortCode);
    }

    shortUrl.registerClick();
    shortUrlRepository.save(shortUrl);

    UrlAccess access = UrlAccess.create(shortUrl.getId(), ipAddress, userAgent, referer);
    urlAccessRepository.save(access);

    log.debug(
        "Redirect processed: {} -> {} (cliques: {})",
        shortCode,
        shortUrl.getOriginalDomain(),
        shortUrl.getClicks());

    return shortUrl.getOriginalUrlValue();
  }

  @Override
  public List<ShortUrlResponse> getAll() {
    log.debug("Fetching all short URLs");
    return shortUrlRepository.findAll().stream()
        .map(url -> ShortUrlResponse.fromDomain(url, baseUrl))
        .toList();
  }

  @Override
  public Optional<ShortUrlResponse> getShortCode(String shorCode) {
    log.debug("Fetching short URL for code: {}", shorCode);

    return shortUrlRepository
        .findByShortCode(shorCode)
        .map(url -> ShortUrlResponse.fromDomain(url, baseUrl));
  }

  @Override
  public void execute(String shortCode) {
    log.info("Deleting short URL with code: {}", shortCode);

    ShortUrl shortUrl =
        shortUrlRepository
            .findByShortCode(shortCode)
            .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));

    shortUrlRepository.deleteById(shortUrl.getId());
    log.info("Short URL with code {} deleted successfully", shortCode);
  }

  private ShortUrl ensureUniqueShortCode(ShortUrl shortUrl) {
    int maxAttempts = 5;
    int attempts = 0;

    while (shortUrlRepository.existsByShortCode(shortUrl.getShortCodeValue())) {
      attempts++;
      if (attempts >= maxAttempts) {
        throw new IllegalStateException(
            "Unable to generate unique short code after " + maxAttempts + " attempts");
      }
      log.warn("Short code collision detected. Generating a new one...(attempt {})", attempts);
      shortUrl = ShortUrl.create(shortUrl.getOriginalUrlValue(), shortUrl.getExpiresAt());
    }

    return shortUrl;
  }

  private String maskUrl(String url) {
    if (url == null || url.length() <= 30) {
      return "****";
    }
    return url.substring(0, 5) + "****" + url.substring(url.length() - 5);
  }
}
