package com.msa.authservice.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
@AllArgsConstructor
public class ErrorResponse<T> {

    private boolean success;
    private T data;
    private T errors;

    public static <T> ErrorResponse<T> success(T data) {
        return new ErrorResponse<>(false, data, null);
    }

    public static <T> ErrorResponse<T> successHeader(T data, HttpHeaders headers) {
        return new ErrorResponse<>(false, data, null);
    }

    public static <T> ErrorResponse<T> fail(T message) {
        return new ErrorResponse<>(false, null, message);
    }
}
