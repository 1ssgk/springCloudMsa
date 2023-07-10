package com.spring.authserver.controller;


import com.spring.authserver.dto.LoginResponseDto;
import com.spring.authserver.dto.LoginRequestDto;
import com.spring.authserver.dto.TokenDto;
import com.spring.authserver.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        LoginResponseDto tokenDto = accountService.loginOAuth(dto, response);

        return ResponseEntity
                .ok()
                .body(tokenDto);
    }

    @GetMapping("/authenticate")
    public ResponseEntity authenticate(HttpServletRequest request) {

        System.out.println("test" + request);
        System.out.println("test header" + request.getHeader(HttpHeaders.AUTHORIZATION));


        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @GetMapping("/reissue")
    public ResponseEntity reissue(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        TokenDto tokenDto = accountService.reissue(principal.getName(),response);

        return ResponseEntity
                .ok()
                .body(tokenDto);
    }
}