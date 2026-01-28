package com.notso.tinyurl.domain.exception;

public class InvalidUrlException extends ShortUrlException {

    public InvalidUrlException(String message) {
        super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
