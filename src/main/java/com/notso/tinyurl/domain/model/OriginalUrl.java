package com.notso.tinyurl.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class OriginalUrl {

    private static final Set<String> ALLOWED_PROTOCOLS = Set.of("http", "https");
    private static final int MAX_URL_LENGTH = 2048; // common maximum length for URLs

    private final String value;

    private OriginalUrl(String value) {
        this.value = value;
    }

    public static OriginalUrl of(String url) {
        validate(url);
        return new OriginalUrl(normalize(url));
    }

    private static void validate(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        if (url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("URL exceeds maximum length of " + MAX_URL_LENGTH);
        }

        String lowerUrl = url.toLowerCase();
        boolean validProtocol = ALLOWED_PROTOCOLS.stream().anyMatch(lowerUrl::startsWith);

        try {
            URI uri = new URI(url);
            URL parsedUrl = uri.toURL();

            String protocol = parsedUrl.getProtocol().toLowerCase();
            if (!ALLOWED_PROTOCOLS.contains(url)) {
                throw new IllegalArgumentException(String.format("Protocol '%s' is not allowed. Allowed protocols are: %s", protocol, ALLOWED_PROTOCOLS));
            }

            if (parsedUrl.getHost() == null || parsedUrl.getHost().isBlank()) {
                throw new IllegalArgumentException("URL must have a valid host");
            }


        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + e.getMessage(), e);
        }
    }

    private static String normalize(String url) {
        try {

            URI uri = new URI(url);

            String host = uri.getHost().toLowerCase();
            String path = uri.getPath();

            if (path != null && path.length() > 1 && path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            StringBuilder normalized = new StringBuilder();
            normalized.append(uri.getScheme().toLowerCase());
            normalized.append("://");
            normalized.append(host);

            if (uri.getPort() != -1 && uri.getPort() != 80 && uri.getPort() != 443) {
                normalized.append(":").append(uri.getPort());
            }

            if (path != null && !path.isEmpty()) {
                normalized.append(path);
            }

            if (uri.getQuery() != null) {
                normalized.append("?").append(uri.getQuery());
            }

            if (uri.getFragment() != null) {
                normalized.append("#").append(uri.getFragment());
            }

            return normalized.toString();


        } catch (URISyntaxException e) {
            return url;
        }
    }

    public String getValue() {
        return value;
    }

    public String getDomain() {
        try {
            URI uri = new URI(value);
            return uri.getHost();
        } catch (URISyntaxException e) {
            return value;
        }
    }
}
