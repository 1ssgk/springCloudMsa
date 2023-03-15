package com.msa.authservice.global.handler;

import com.msa.authservice.global.dto.ErrorResponse;
import com.msa.authservice.global.exception.ConflictException;
import com.msa.authservice.global.exception.ForbiddenException;
import com.msa.authservice.global.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final Map<String, Object> errors = new HashMap();

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = {ConflictException.class})
    @ResponseBody
    protected ErrorResponse badRequest(RuntimeException ex, WebRequest request) {
        errors.put("code", CONFLICT.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseBody
    protected ErrorResponse forbidden(RuntimeException ex, WebRequest request) {
        errors.put("code", FORBIDDEN.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    protected ErrorResponse notFound(RuntimeException ex, WebRequest request) {
        errors.put("code", NOT_FOUND.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = {SecurityException.class, SignatureException.class})
    @ResponseBody
    protected ErrorResponse unauthorized(RuntimeException ex, WebRequest request) {
        errors.put("code", UNAUTHORIZED.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler
    @ResponseBody
    protected ErrorResponse methodNotAllowed(RuntimeException ex, WebRequest request) {
        errors.put("code", METHOD_NOT_ALLOWED.value());
        errors.put("message", ex.getMessage());
        return ErrorResponse.fail(errors);
    }
}
