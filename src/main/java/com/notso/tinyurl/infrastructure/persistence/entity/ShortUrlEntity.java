package com.notso.tinyurl.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "short_urls",
    indexes = {
      @Index(name = "idx_short_code", columnList = "short_code", unique = true),
      @Index(name = "idx_created_at", columnList = "created_at")
    })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlEntity {

  @Id
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "short_code", nullable = false, unique = true, length = 10)
  private String shortCode;

  @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
  private String originalUrl;

  @Column(nullable = false)
  private Long clicks;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;
}
