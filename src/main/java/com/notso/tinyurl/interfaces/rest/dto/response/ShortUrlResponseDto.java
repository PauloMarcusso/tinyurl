package com.notso.tinyurl.interfaces.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.notso.tinyurl.application.dto.response.ShortUrlResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "Response DTO for Short URL information")
public class ShortUrlResponseDto {

  @Schema(
      description = "Unique identifier of the short URL",
      example = "123e4567-e89b-12d3-a456-426614174000")
  private final UUID id;

  @Schema(description = "The shortened URL", example = "https://tiny.url/abc123")
  private final String shortCode;

  @Schema(description = "The original URL", example = "https://www.example.com/some/long/url")
  private final String originalUrl;

  @Schema(description = "Number of clicks on the short URL", example = "42")
  private final long clicks;

  @Schema(description = "Creation timestamp of the short URL", example = "2024-01-01T12:00:00")
  private final LocalDateTime createdAt;

  @Schema(description = "Expiration timestamp of the short URL", example = "2024-12-31T23:59:59")
  private final LocalDateTime expiresAt;

  @Schema(description = "Indicates whether the short URL has expired", example = "false")
  private final boolean expired;

  public static ShortUrlResponseDto fromApplication(ShortUrlResponse response) {
    return ShortUrlResponseDto.builder()
        .id(response.getId())
        .shortCode(response.getShortUrl())
        .originalUrl(response.getOriginalUrl())
        .clicks(response.getClicks())
        .createdAt(response.getCreatedAt())
        .expiresAt(response.getExpiresAt())
        .expired(response.isExpired())
        .build();
  }
}
