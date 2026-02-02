package com.notso.tinyurl.infrastructure.persistence.repository;

import com.notso.tinyurl.infrastructure.persistence.entity.ShortUrlEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShortUrlRepository extends JpaRepository<ShortUrlEntity, UUID> {

  Optional<ShortUrlEntity> findByShortCode(String shortCode);

  boolean existsByShortCode(String shortCode);
}
