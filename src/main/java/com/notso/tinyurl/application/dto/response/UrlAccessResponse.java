package com.notso.tinyurl.application.dto.response;

import com.notso.tinyurl.domain.model.UrlAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UrlAccessResponse {

    private final UUID id;
    private final String ipAddress;
    private final String userAgent;
    private final String referer;
    private final LocalDateTime accessedAt;
    private final boolean mobile;

    public static UrlAccessResponse fromDomain(UrlAccess urlAccess) {
        return UrlAccessResponse.builder().id(urlAccess.getId()).ipAddress(maskIpAddress(urlAccess.getIpAddress())).userAgent(urlAccess.getUserAgent()).referer(urlAccess.getReferer()).accessedAt(urlAccess.getAccessedAt()).mobile(urlAccess.isMobileAccess()).build();
    }

    private static String maskIpAddress(String ip) {
        if (ip == null || ip.isBlank()) {
            return null;
        }
        int lastDot = ip.lastIndexOf('.');
        if (lastDot > 0) {
            return ip.substring(0, lastDot) + ".***";

        }

        return ip;
    }
}
