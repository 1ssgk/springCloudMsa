package com.msa.authservice.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> {
    private boolean success;
    private T data;
    private T errors;

    public static <T> ResponseEntity<SuccessResponse> success(T data) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(
                        true,
                        data,
                        ""
                ));
    }
}
