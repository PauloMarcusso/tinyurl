package com.notso.tinyurl.application.port.in;

import com.notso.tinyurl.application.dto.request.CreateShortUrlRequest;
import com.notso.tinyurl.application.dto.response.ShortUrlResponse;

public interface CreateShortUrlUseCase {

    ShortUrlResponse execute(CreateShortUrlRequest request);
}
