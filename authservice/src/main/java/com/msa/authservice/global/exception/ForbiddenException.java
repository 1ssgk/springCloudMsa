package com.msa.authservice.global.exception;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ForbiddenException(String message) {
        super(message, ErrorCode.FORBIDDEN_EXCEPTION);
    }
}
