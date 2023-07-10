package com.ws.orderservice.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class OrderController {

    @GetMapping("/test")
    public ResponseEntity test(HttpServletRequest request) {
        System.out.println("오오오옹");
        log.info("request ={}",request.getHeader(HttpHeaders.AUTHORIZATION));

        return ResponseEntity
                .ok()
                .body("success");
    }
}
