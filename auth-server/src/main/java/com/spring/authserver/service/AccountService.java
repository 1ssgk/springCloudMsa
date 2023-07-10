package com.spring.authserver.service;

import com.spring.authserver.dto.LoginResponseDto;
import com.spring.authserver.dto.LoginRequestDto;
import com.spring.authserver.entity.Account;

public interface AccountService {
    public LoginResponseDto loginOAuth(LoginRequestDto dto);

    public Account createOrUpdateUser(Account account);

}
