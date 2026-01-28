package com.notso.tinyurl.application.dto.response;

import com.notso.tinyurl.domain.model.ShortUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ShortUrlResponse {

    private final UUID id;
    private final String shorCode;
    private final String shortUrl;
    private final String originalUrl;
    private final long clicks;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private final boolean expired;


    public static ShortUrlResponse fromDomain(ShortUrl shortUrl, String baseUrl) {
        return ShortUrlResponse.builder()
                .id(shortUrl.getId())
                .shorCode(shortUrl.getShortCodeValue())
                .shortUrl(buildShortUrl(baseUrl, shortUrl.getShortCodeValue()))
                .originalUrl(shortUrl.getOriginalUrlValue())
                .clicks(shortUrl.getClicks())
                .createdAt(shortUrl.getCreatedAt())
                .expiresAt(shortUrl.getExpiresAt())
                .expired(shortUrl.isExpired())
                .build();
    }

    private static String buildShortUrl(String baseUrl, String shortCode) {
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        return normalizedBase + "/" + shortCode;
    }
}
