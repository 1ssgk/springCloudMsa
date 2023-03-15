package com.msa.authservice.global.handler;

import com.msa.authservice.global.dto.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ErrorResponse processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        List<Map> errors = new ArrayList<>();

        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            Map<String, Object> error = new HashMap<>();
            error.put("filed", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return ErrorResponse.fail(errors);
    }
}