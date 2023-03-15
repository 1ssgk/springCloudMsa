package com.msa.authservice.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.authservice.global.dto.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.msa.authservice.global.exception.ErrorStatusCode.FORBIDDEN;

@RequiredArgsConstructor
@Getter
public enum JwtErrorCode {
    SECURITY(FORBIDDEN, "잘못된 JWT 서명입니다."),
    MALFORMED_JWT(FORBIDDEN, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT(FORBIDDEN, "만료된 JWT 토큰입니다."),
    UN_SUPPORTED_JWT(FORBIDDEN, "지원되지 않는 JWT 토큰입니다."),
    ILLEGAL_ARGUMENT(FORBIDDEN, "JWT 토큰이 잘못되었습니다."),
    DEFAULT(FORBIDDEN, "잘못된 JWT 토큰입니다.");

    private final ErrorStatusCode code;
    private final String msg;

    public static HttpServletResponse setting(HttpServletResponse response, String errType) {
        ObjectMapper obj = new ObjectMapper();
        JwtErrorCode error = JwtErrorCode.valueOf(errType);

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(error.code.getStatus());
            response.getWriter().write(obj.writeValueAsString(ErrorResponse.fail(error.msg)));
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpServletResponse setting(HttpServletResponse response, String errType, String errorMsg) {
        ObjectMapper obj = new ObjectMapper();
        JwtErrorCode error = JwtErrorCode.valueOf(errType);
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(error.code.getStatus());
            response.getWriter().write(obj.writeValueAsString(ErrorResponse.fail(errorMsg)));
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
