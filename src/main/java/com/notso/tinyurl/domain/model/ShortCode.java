package com.notso.tinyurl.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.security.SecureRandom;

@EqualsAndHashCode
@ToString
public final class ShortCode {

    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int DEFAULT_LENGTH = 8;
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 10;

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String value;

    public ShortCode(String value) {
        this.value = value;
    }

    public static ShortCode generate() {
        return generate(DEFAULT_LENGTH);
    }

    public static ShortCode generate(int length) {
        validateLength(length);
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(BASE62_CHARS.length());
            code.append(BASE62_CHARS.charAt(i));
        }
        return new ShortCode(code.toString());
    }

    public static ShortCode of(String value) {
        validate(value);
        return new ShortCode(value);
    }

    private static void validate(String value) {

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Short code length must be between " + MIN_LENGTH + " and " + MAX_LENGTH);
        }

        for (char c : value.toCharArray()) {
            if (BASE62_CHARS.indexOf(c) == -1) {
                throw new IllegalArgumentException("Short code contains invalid character: " + c);
            }
        }

    }

    private static void validateLength(int length) {
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException("Short code length must be between " + MIN_LENGTH + " and " + MAX_LENGTH);
        }
    }

    public String getValue() {
        return value;
    }
}



