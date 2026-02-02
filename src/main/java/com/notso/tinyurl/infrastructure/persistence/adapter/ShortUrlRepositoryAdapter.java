package com.notso.tinyurl.infrastructure.persistence.adapter;

import com.notso.tinyurl.domain.model.ShortUrl;
import com.notso.tinyurl.domain.repository.ShortUrlRepository;
import com.notso.tinyurl.infrastructure.persistence.entity.ShortUrlEntity;
import com.notso.tinyurl.infrastructure.persistence.mapper.ShortUrlMapper;
import com.notso.tinyurl.infrastructure.persistence.repository.JpaShortUrlRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortUrlRepositoryAdapter implements ShortUrlRepository {

  private final JpaShortUrlRepository jpaShortUrlRepository;

  @Override
  public ShortUrl save(ShortUrl shortUrl) {
    ShortUrlEntity entity = ShortUrlMapper.toEntity(shortUrl);
    ShortUrlEntity savedEntity = jpaShortUrlRepository.save(entity);
    return ShortUrlMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<ShortUrl> findByShortCode(String shortCode) {
    return jpaShortUrlRepository.findByShortCode(shortCode).map(ShortUrlMapper::toDomain);
  }

  @Override
  public Optional<ShortUrl> findById(UUID id) {
    return jpaShortUrlRepository.findById(id).map(ShortUrlMapper::toDomain);
  }

  @Override
  public List<ShortUrl> findAll() {
    return jpaShortUrlRepository.findAll().stream().map(ShortUrlMapper::toDomain).toList();
  }

  @Override
  public void deleteById(UUID id) {
    jpaShortUrlRepository.deleteById(id);
  }

  @Override
  public boolean existsByShortCode(String shortCode) {
    return jpaShortUrlRepository.existsByShortCode(shortCode);
  }
}
