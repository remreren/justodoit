package com.remreren.justodoit.exception;

public record ErrorResponse(
        String traceId,
        ErrorMessage error
) {
}
