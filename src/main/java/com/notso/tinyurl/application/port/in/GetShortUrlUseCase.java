package com.notso.tinyurl.application.port.in;

import com.notso.tinyurl.application.dto.response.ShortUrlResponse;

import java.util.List;
import java.util.Optional;

public interface GetShortUrlUseCase {

    List<ShortUrlResponse> getAll();

    Optional<ShortUrlResponse> getShortCode(String shorCode);

}
