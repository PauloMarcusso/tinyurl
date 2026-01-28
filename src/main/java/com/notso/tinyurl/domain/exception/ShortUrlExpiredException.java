package com.notso.tinyurl.domain.exception;

public class ShortUrlExpiredException extends ShortUrlException {

    private final String shortCode;

    public ShortUrlExpiredException(String shortCode) {
        super(String.format("Url with short code '%s' has expired", shortCode));
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }
}
