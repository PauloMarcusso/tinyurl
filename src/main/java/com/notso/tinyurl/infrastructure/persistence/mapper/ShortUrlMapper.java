package com.notso.tinyurl.infrastructure.persistence.mapper;

import com.notso.tinyurl.domain.model.ShortUrl;
import com.notso.tinyurl.infrastructure.persistence.entity.ShortUrlEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShortUrlMapper {

  public static ShortUrlEntity toEntity(ShortUrl domain) {
    if (domain == null) {
      return null;
    }
    return ShortUrlEntity.builder()
        .id(domain.getId())
        .shortCode(domain.getShortCodeValue())
        .originalUrl(domain.getOriginalUrlValue())
        .clicks(domain.getClicks())
        .createdAt(domain.getCreatedAt())
        .expiresAt(domain.getExpiresAt())
        .build();
  }

  public static ShortUrl toDomain(ShortUrlEntity entity) {
    if (entity == null) {
      return null;
    }
    return ShortUrl.reconstitute(
        entity.getId(),
        entity.getShortCode(),
        entity.getOriginalUrl(),
        entity.getCreatedAt(),
        entity.getExpiresAt(),
        entity.getClicks());
  }
}
