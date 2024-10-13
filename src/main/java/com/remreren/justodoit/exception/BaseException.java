package com.remreren.justodoit.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int status;
    private final ErrorMessage errorPayload;

    public BaseException(ErrorMessage errorPayload) {
        super(errorPayload.message());
        this.errorPayload = errorPayload;
        this.status = errorPayload.status();
    }

    public static BaseException of(ErrorMessage errorPayload) {
        return new BaseException(errorPayload);
    }
}
