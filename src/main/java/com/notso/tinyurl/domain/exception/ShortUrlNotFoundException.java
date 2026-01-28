package com.notso.tinyurl.domain.exception;

public class ShortUrlNotFoundException extends ShortUrlException {

    private final String shortCode;

    public ShortUrlNotFoundException(String shortCode) {
        super(String.format("Url with short code '%s' not found", shortCode));
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }
}
