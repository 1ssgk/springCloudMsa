package com.msa.authservice.domain.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class AuthController {

    @Value("${wonseok.val}")
    String val;

    @GetMapping("/test")
    public String test(){

        System.out.println("HI im auth :: "+val);

        return "Hi im auth!";
    }
}
