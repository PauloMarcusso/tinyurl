package com.notso.tinyurl.domain.repository;

import com.notso.tinyurl.domain.model.UrlAccess;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UrlAccessRepository {

    UrlAccess save(UrlAccess urlAccess);

    List<UrlAccess> findByShortUrlId(UUID shortUrlId);

    long countByShortUrlId(UUID shortUrlId);

    List<UrlAccess> findByShortUrlIdAndAccessedAtBetween(UUID shortUrlId, LocalDateTime start, LocalDateTime end);

}
