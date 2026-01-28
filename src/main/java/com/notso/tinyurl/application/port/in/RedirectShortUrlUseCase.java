package com.notso.tinyurl.application.port.in;

public interface RedirectShortUrlUseCase {

    String execute(String shortUrlCode, String ipAddress, String userAgent, String referer);

}
