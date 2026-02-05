package com.notso.tinyurl.interfaces.rest.controller;

import com.notso.tinyurl.application.port.in.CreateShortUrlUseCase;
import com.notso.tinyurl.application.port.in.DeleteShortUrlUseCase;
import com.notso.tinyurl.application.port.in.GetShortUrlUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/urls")
@RequiredArgsConstructor
@Tag(name = "Short URL Management", description = "APIs for managing short URLs")
public class ShortUrlController {

  private final CreateShortUrlUseCase createShortUrlUseCase;
  private final GetShortUrlUseCase getShortUrlUseCase;
  private final DeleteShortUrlUseCase deleteShortUrlUseCase;



}
