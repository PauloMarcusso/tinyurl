package com.notso.tinyurl.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;


@ToString
@Getter
@RequiredArgsConstructor
public class ShortUrl {

    private final UUID id;
    private final ShortCode shortCode;
    private final OriginalUrl originalUrl;
    private final LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private long clicks;

    private ShortUrl(UUID id, ShortCode shortCode, OriginalUrl originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt, long clicks) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.shortCode = Objects.requireNonNull(shortCode, "shortCode cannot be null");
        this.originalUrl = Objects.requireNonNull(originalUrl, "originalUrl cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.expiresAt = expiresAt;
        this.clicks = clicks;

        validateInvariants();
    }

    public static ShortUrl create(String originalUrl) {
        return create(originalUrl, null);
    }

    public static ShortUrl create(String originalUrl, LocalDateTime expiresAt) {
        return new ShortUrl(
                UUID.randomUUID(),
                ShortCode.generate(),
                OriginalUrl.of(originalUrl),
                LocalDateTime.now(),
                expiresAt,
                0L
        );
    }

    public static ShortUrl reconstitute(UUID id, String shortCode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt, long clicks) {
        return new ShortUrl(id, ShortCode.of(shortCode), OriginalUrl.of(originalUrl), createdAt, expiresAt, clicks);
    }

    private void validateInvariants() {
        if (clicks < 0) {
            throw new IllegalStateException("Clicks cannot be negative");
        }

        if (expiresAt != null && expiresAt.isBefore(createdAt)) {
            throw new IllegalStateException("Expiration date cannot be before creation date");
        }
    }

    public boolean isExpired() {
        if (expiresAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isActive() {
        return !isExpired();
    }

    public void registerClick() {
        if (isExpired()) {
            throw new IllegalStateException("Cannot register click on expired ShortUrl");
        }
        this.clicks++;
    }

    public void updateExpiration(LocalDateTime newExpiresAt) {
        if (newExpiresAt != null && newExpiresAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("New expiration date cannot be before creation date");
        }
        this.expiresAt = newExpiresAt;
    }

    public void removeExpiration() {
        this.expiresAt = null;
    }

    public long getDaysActive() {
        return ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
    }

    public String getOriginalDomain() {
        return originalUrl.getDomain();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrl shortUrl = (ShortUrl) o;
        return id.equals(shortUrl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
