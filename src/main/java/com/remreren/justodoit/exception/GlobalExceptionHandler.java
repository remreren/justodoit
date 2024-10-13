package com.remreren.justodoit.exception;

import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Object> handleBaseException(BaseException exception) {
        log.error(exception.getErrorPayload().message(), exception);

        String traceId = tracer.currentSpan().context().traceIdString();

        return ResponseEntity
                .status(exception.getStatus())
                .body(new ErrorResponse(traceId, exception.getErrorPayload()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("Bad credentials", exception);

        String traceId = tracer.currentSpan().context().traceIdString();

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(traceId, ErrorMessages.BAD_CREDENTIALS));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        log.error("Unexpected error occurred", exception);

        String traceId = tracer.currentSpan().context().traceIdString();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(traceId, ErrorMessages.UNKNOWN_EXCEPTION));
    }
}
