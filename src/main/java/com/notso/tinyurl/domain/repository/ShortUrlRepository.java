package com.notso.tinyurl.domain.repository;

import com.notso.tinyurl.domain.model.ShortUrl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShortUrlRepository {

    ShortUrl save(ShortUrl shortUrl);

    Optional<ShortUrl> findByShortCode(String shortCode);

    Optional<ShortUrl> findById(UUID id);

    List<ShortUrl> findAll();

    void deleteById(UUID id);

    boolean existsByShortCode(String shortCode);
}
