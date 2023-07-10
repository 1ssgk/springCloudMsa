package com.msa.userservice.domain.user.controller;

import com.msa.userservice.domain.user.dto.UserSignUpDto;
import com.msa.userservice.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }


    // 처음 요청 시 request url을... 흠... 어떻게 암?
    @GetMapping("/oauth2/google/login")
    public String callback(@RequestParam(name = "code") String code){
        log.info("google callback ={}",code);
        return "success";
    }
}
