package com.msa.authservice.global.exception;

public class NoSuchAlgorithmException extends BusinessException {
    public NoSuchAlgorithmException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NoSuchAlgorithmException(String message) {
        super(message, ErrorCode.NO_SOUCH_ALGORITHM);
    }
}
