package com.remreren.justodoit.exception;

public record ErrorMessage(
        int status,
        String code,
        String message
) {
}
