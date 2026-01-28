package com.notso.tinyurl.infrastructure.persistence.mapper;

import com.notso.tinyurl.domain.model.UrlAccess;
import com.notso.tinyurl.infrastructure.persistence.entity.UrlAccessEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlAccessMapper {

  public static UrlAccessEntity toEntity(UrlAccess domain) {
    if (domain == null) {
      return null;
    }

    return UrlAccessEntity.builder()
        .id(domain.getId())
        .shortUrlId(domain.getShortUrlId())
        .ipAddress(domain.getIpAddress())
        .userAgent(domain.getUserAgent())
        .referer(domain.getReferer())
        .accessedAt(domain.getAccessedAt())
        .build();
  }

  public static UrlAccess toDomain(UrlAccessEntity entity) {
    if (entity == null) {
      return null;
    }

    return UrlAccess.reconstitute(
        entity.getId(),
        entity.getShortUrlId(),
        entity.getIpAddress(),
        entity.getUserAgent(),
        entity.getReferer(),
        entity.getAccessedAt());
  }
}
