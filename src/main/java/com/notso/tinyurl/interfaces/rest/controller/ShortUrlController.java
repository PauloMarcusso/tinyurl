package com.notso.tinyurl.interfaces.rest.controller;

import com.notso.tinyurl.application.dto.request.CreateShortUrlRequest;
import com.notso.tinyurl.application.dto.response.ShortUrlResponse;
import com.notso.tinyurl.application.port.in.CreateShortUrlUseCase;
import com.notso.tinyurl.application.port.in.DeleteShortUrlUseCase;
import com.notso.tinyurl.application.port.in.GetShortUrlUseCase;
import com.notso.tinyurl.interfaces.rest.dto.request.CreateShortUrlRequestDto;
import com.notso.tinyurl.interfaces.rest.dto.response.ApiErrorResponseDto;
import com.notso.tinyurl.interfaces.rest.dto.response.ShortUrlResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
@Tag(name = "URL Shortener", description = "API for managing shortened URLs")
public class ShortUrlController {

  private final CreateShortUrlUseCase createShortUrlUseCase;
  private final GetShortUrlUseCase getShortUrlUseCase;
  private final DeleteShortUrlUseCase deleteShortUrlUseCase;

  @PostMapping
  @Operation(
      summary = "Create short URL",
      description = " Receive a long URL and returns a shortened version")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "URL sucessfully shortened",
        content = @Content(schema = @Schema(implementation = ShortUrlResponseDto.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid URL",
        content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
  })
  public ResponseEntity<ShortUrlResponseDto> create(
      @Valid @RequestBody CreateShortUrlRequestDto requestDto) {

    log.info("Received request to shorten URL: {}", requestDto.getUrl());

    // Converter DTO da Interface -> DTO da Application
    CreateShortUrlRequest request =
        CreateShortUrlRequest.builder()
            .url(requestDto.getUrl())
            .expiresAt(requestDto.getExpiresAt())
            .customCode(requestDto.getCustomCode())
            .build();

    ShortUrlResponse response = createShortUrlUseCase.execute(request);

    ShortUrlResponseDto responseDto = ShortUrlResponseDto.fromApplication(response);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  // ==================== GET ALL ====================

  @GetMapping
  @Operation(summary = "List URLs", description = "Returns a list of all shortened URLs")
  @ApiResponse(responseCode = "200", description = "List of URLs")
  public ResponseEntity<List<ShortUrlResponseDto>> getAll() {
    log.debug("Listing all URLs");

    List<ShortUrlResponseDto> urls =
        getShortUrlUseCase.getAll().stream().map(ShortUrlResponseDto::fromApplication).toList();

    return ResponseEntity.ok(urls);
  }

  // ==================== GET BY CODE ====================

  @GetMapping("/{shortCode}")
  @Operation(
      summary = "Search URL by code",
      description = "Returns the details of a specific shortened URL")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "URL found"),
    @ApiResponse(
        responseCode = "404",
        description = "URL not found",
        content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
  })
  public ResponseEntity<ShortUrlResponseDto> getByCode(@PathVariable String shortCode) {
    log.debug("Searching URL by code: {}", shortCode);

    return getShortUrlUseCase
        .getShortCode(shortCode)
        .map(ShortUrlResponseDto::fromApplication)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{shortCode}")
  @Operation(summary = "Remove URL", description = "Remove URL by short code")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "URL successfully removed"),
    @ApiResponse(
        responseCode = "404",
        description = "URL not found",
        content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
  })
  public ResponseEntity<Void> delete(@PathVariable String shortCode) {
    log.info("Removing URL: {}", shortCode);

    deleteShortUrlUseCase.execute(shortCode);

    return ResponseEntity.noContent().build();
  }
}
