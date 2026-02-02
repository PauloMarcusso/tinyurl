package com.notso.tinyurl.infrastructure.persistence.adapter;

import static java.util.stream.Collectors.toList;

import com.notso.tinyurl.domain.model.UrlAccess;
import com.notso.tinyurl.domain.repository.UrlAccessRepository;
import com.notso.tinyurl.infrastructure.persistence.entity.UrlAccessEntity;
import com.notso.tinyurl.infrastructure.persistence.mapper.UrlAccessMapper;
import com.notso.tinyurl.infrastructure.persistence.repository.JpaUrlAccessRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlAccessRepositoryAdapter implements UrlAccessRepository {

  private final JpaUrlAccessRepository jpaUrlAccessRepository;

  @Override
  public UrlAccess save(UrlAccess urlAccess) {
    UrlAccessEntity entity = UrlAccessMapper.toEntity(urlAccess);
    UrlAccessEntity saved = jpaUrlAccessRepository.save(entity);
    return UrlAccessMapper.toDomain(saved);
  }

  @Override
  public List<UrlAccess> findByShortUrlId(UUID shortUrlId) {
    return jpaUrlAccessRepository.findByShortUrlId(shortUrlId)
        .stream()
        .map(UrlAccessMapper::toDomain)
        .toList();
  }

  @Override
  public long countByShortUrlId(UUID shortUrlId) {
    return jpaUrlAccessRepository.countByShortUrlId(shortUrlId);
  }

  @Override
  public List<UrlAccess> findByShortUrlIdAndAccessedAtBetween(UUID shortUrlId, LocalDateTime start,
      LocalDateTime end) {
    return jpaUrlAccessRepository.findByShortUrlIdAndAccessedAtBetween(shortUrlId, start, end)
        .stream()
        .map(UrlAccessMapper::toDomain)
        .collect(toList());
  }
}
