package com.notso.tinyurl.domain.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
public class UrlAccess {

    private final UUID id;
    private final UUID shortUrlId;
    private final String ipAddress;
    private final String userAgent;
    private final String referer;
    private final LocalDateTime accessedAt;

    public UrlAccess(UUID id, UUID shortUrlId, String ipAddress, String userAgent, String referer, LocalDateTime accessedAt) {
        this.id = Objects.requireNonNull(id,"id cannot be null");
        this.shortUrlId = Objects.requireNonNull(shortUrlId,"shortUrlId cannot be null");
        this.ipAddress = sanitize(ipAddress);
        this.userAgent = sanitize(userAgent);
        this.referer = referer;
        this.accessedAt = Objects.requireNonNull(accessedAt,"accessedAt cannot be null");
    }

    public static UrlAccess create(UUID shortUrlId, String ipAddress, String userAgent, String referer) {
        return new UrlAccess(
                UUID.randomUUID(),
                shortUrlId,
                ipAddress,
                userAgent,
                referer,
                LocalDateTime.now()
        );
    }

    public static UrlAccess reconstitute(UUID id, UUID shortUrlId, String ipAddress, String userAgent, String referer, LocalDateTime accessedAt) {
        return new UrlAccess(id, shortUrlId, ipAddress, userAgent, referer, accessedAt);
    }

    private static String sanitize(String value) {
        if (value == null || value.isBlank() || "null".equalsIgnoreCase(value)) {
            return null;
        }
        return value.length() > 500 ? value.substring(0, 500) : value.trim();
    }

    public boolean isMobileAccess() {
        if (userAgent == null) {
            return false;
        }
        String ua = userAgent.toLowerCase();
        return ua.contains("mobile") || ua.contains("android") || ua.contains("iphone") || ua.contains("ipad");
    }

    public boolean hasReferer() {
        return referer != null && !referer.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlAccess urlAccess = (UrlAccess) o;
        return id.equals(urlAccess.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
