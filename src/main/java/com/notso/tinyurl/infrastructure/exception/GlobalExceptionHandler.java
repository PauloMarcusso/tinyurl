package com.notso.tinyurl.infrastructure.exception;

import com.notso.tinyurl.domain.exception.InvalidUrlException;
import com.notso.tinyurl.domain.exception.ShortUrlExpiredException;
import com.notso.tinyurl.domain.exception.ShortUrlNotFoundException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ShortUrlNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(ShortUrlNotFoundException ex) {
    log.warn("Short URL not found: {}", ex.getShortCode());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", ex.getMessage()));
  }

  @ExceptionHandler(ShortUrlExpiredException.class)
  public ResponseEntity<ErrorResponse> handleExpired(ShortUrlExpiredException ex) {
    log.warn("Short URL expired: {}", ex.getShortCode());

    return ResponseEntity.status(HttpStatus.GONE)
        .body(ErrorResponse.of(HttpStatus.GONE.value(), "LINK_EXPIRED", ex.getMessage()));
  }

  @ExceptionHandler(InvalidUrlException.class)
  public ResponseEntity<ErrorResponse> handleInvalidUrl(InvalidUrlException ex) {
    log.warn("Invalid URL provided: {}", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "INVALID_URL", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    log.error("An unexpected error occurred", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred"));
  }

  @Getter
  @Setter
  @Builder
  public static class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final LocalDateTime timeStamp;

    public static ErrorResponse of(int status, String error, String message) {
      return ErrorResponse.builder()
          .status(status)
          .error(error)
          .message(message)
          .timeStamp(LocalDateTime.now())
          .build();
    }
  }
}
