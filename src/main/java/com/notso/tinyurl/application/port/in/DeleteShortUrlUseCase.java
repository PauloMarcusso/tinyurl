package com.notso.tinyurl.application.port.in;

public interface DeleteShortUrlUseCase {

    void execute(String shortCode);

}
