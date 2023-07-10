package com.spring.authserver.service;

import com.spring.authserver.dto.LoginResponseDto;
import com.spring.authserver.dto.LoginRequestDto;
import com.spring.authserver.dto.TokenDto;
import com.spring.authserver.entity.Account;

import javax.servlet.http.HttpServletResponse;

public interface AccountService {
    public LoginResponseDto loginOAuth(LoginRequestDto dto, HttpServletResponse response);

    public Account createOrUpdateUser(Account account);

    public TokenDto reissue(String name, HttpServletResponse response);
}
