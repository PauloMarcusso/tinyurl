package com.notso.tinyurl.infrastructure.persistence.repository;

import com.notso.tinyurl.infrastructure.persistence.entity.UrlAccessEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUrlAccessRepository extends JpaRepository<UrlAccessEntity, UUID> {

  List<UrlAccessEntity> findByShortUrlId(UUID shortUrlId);

  long countByShortUrlId(UUID shortUrlId);

  List<UrlAccessEntity> findByShortUrlIdAndAccessedAtBetween(
      UUID shortUrlId, LocalDateTime start, LocalDateTime end);
}
