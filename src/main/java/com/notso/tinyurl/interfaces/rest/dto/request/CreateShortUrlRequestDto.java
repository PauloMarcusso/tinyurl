package com.notso.tinyurl.interfaces.rest.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortUrlRequestDto {

  @NotBlank(message = "URL must not be blank")
  @Size(max = 2048, message = "URL must not exceed 2048 characters")
  @Pattern(regexp = "ˆhttps?://.*", message = "URL must start with http:// or https://")
  private String url;

  @Future(message = "Expiration date must be in the future")
  private LocalDateTime expiresAt;

  @Size(min = 6, max = 10, message = "Custom short code must be between 6 and 10 characters")
  @Pattern(
      regexp = "^[a-zA-Z0-9]*$",
      message = "Custom short code can only contain alphanumeric characters")
  private String customCode;
}
