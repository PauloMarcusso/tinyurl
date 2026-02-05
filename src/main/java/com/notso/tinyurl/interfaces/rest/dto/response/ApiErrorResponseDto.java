package com.notso.tinyurl.interfaces.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "Response DTO for API error information")
public class ApiErrorResponseDto {

  @Schema(description = "Http Code", example = "404")
  private final int status;

  @Schema(description = "Error code", example = "NOT_FOUND")
  private final String error;

  @Schema(description = "Message of the error", example = "The requested resource was not found")
  private final String message;

  @Schema(description = "Timestamp of when the error occurred", example = "2024-01-01T12:00:00")
  private final LocalDateTime timestamp;

  @Schema(description = "List of field errors, if applicable")
  private final List<FieldError> fieldErrors;

  public static ApiErrorResponseDto of(int status, String error, String message) {
    return ApiErrorResponseDto.builder()
        .status(status)
        .error(error)
        .message(message)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static ApiErrorResponseDto withFieldErrors(
      int status, String error, String message, List<FieldError> fieldErrors) {
    return ApiErrorResponseDto.builder()
        .status(status)
        .error(error)
        .message(message)
        .timestamp(LocalDateTime.now())
        .fieldErrors(fieldErrors)
        .build();
  }

  @Getter
  @Builder
  @AllArgsConstructor
  public static class FieldError {

    @Schema(description = "Name of the field with an error", example = "originalUrl")
    private final String field;

    @Schema(description = "Error message for the field", example = "must be a valid URL")
    private final String message;
  }
}
