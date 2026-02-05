package com.notso.tinyurl.interfaces.rest.controller;

import com.notso.tinyurl.application.port.in.RedirectShortUrlUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Hidden
public class RedirectController {

  private final RedirectShortUrlUseCase redirectUseCase;

  @GetMapping("/{shortCode}")
  public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request) {

    log.debug("Redirect request received for short code: {}", shortCode);

    String ipAddress = extractClientIp(request);
    String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    String referer = request.getHeader(HttpHeaders.REFERER);

    String originalUrl = redirectUseCase.execute(shortCode, ipAddress, userAgent, referer);

    log.debug("Redirecting {} -> {}", shortCode, maskUrl(originalUrl));

    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
  }

  private String extractClientIp(HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isBlank()) {
      return xForwardedFor.split(",")[0].trim();
    }

    String xRealIp = request.getHeader("X-Real-IP");
    if (xRealIp != null && !xRealIp.isBlank()) {
      return xRealIp.trim();
    }

    return request.getRemoteAddr();
  }

  private String maskUrl(String url) {
    if (url == null || url.length() <= 50) {
      return url;
    }
    return url.substring(0, 5) + "...";
  }
}
