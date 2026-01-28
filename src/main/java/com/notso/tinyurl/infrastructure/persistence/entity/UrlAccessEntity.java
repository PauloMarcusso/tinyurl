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
    name = "url_accesses",
    indexes = {
      @Index(name = "idx_short_url_id", columnList = "shortUrlId"),
      @Index(name = "idx_accessed_at", columnList = "accessedAt")
    })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlAccessEntity {

  @Id
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "short_url_id", nullable = false, columnDefinition = "UUID")
  private UUID shortUrlId;

  @Column(name = "ip_address", length = 45)
  private String ipAddress;

  @Column(name = "user_agent", columnDefinition = "TEXT")
  private String userAgent;

  @Column(columnDefinition = "TEXT")
  private String referer;

  @Column(name = "accessed_at", nullable = false)
  private LocalDateTime accessedAt;
}
