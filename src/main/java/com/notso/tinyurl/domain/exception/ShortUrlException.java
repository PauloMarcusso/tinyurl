package com.notso.tinyurl.domain.exception;

public class ShortUrlException extends RuntimeException{

    protected ShortUrlException(String message) {
        super(message);
    }

    protected ShortUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
