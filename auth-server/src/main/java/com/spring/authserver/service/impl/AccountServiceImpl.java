package com.spring.authserver.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.spring.authserver.dto.LoginResponseDto;
import com.spring.authserver.dto.LoginRequestDto;
import com.spring.authserver.dto.TokenDto;
import com.spring.authserver.entity.Account;
import com.spring.authserver.exception.NoSuchDataException;
import com.spring.authserver.repository.AccountRepository;
import com.spring.authserver.service.AccountService;
import com.spring.authserver.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final JwtUtils jwtUtils;
    private final GoogleIdTokenVerifier verifier;

    public AccountServiceImpl(
            @Value("${app.googleClientId}") String clientId,
            AccountRepository accountRepository,
            JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        this.verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    /**
     * Google OAuth 이후 처리
     */
    @Override
    public LoginResponseDto loginOAuth(LoginRequestDto dto, HttpServletResponse response) {
        Account account = verifyIdToken(dto.getIdToken());

        if (account == null) {
            throw new IllegalStateException();
        }

        account = createOrUpdateUser(account);

        String accessToken = jwtUtils.createToken(account, false);
        String refreshToken = jwtUtils.createToken(account, true);

        setRefreshToken(refreshToken, response);

        String userName = account.getLastName() + account.getFirstName();
        String email = account.getEmail();

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .name(userName)
                .build();
    }

    @Transactional
    @Override
    public Account createOrUpdateUser(Account account) {
        Account existedAccount = accountRepository.findByEmail(account.getEmail()).orElse(null);

        if (existedAccount == null) {
            account.setRoles("ROLE_USER");

            accountRepository.save(account);
            return account;
        }

        existedAccount.setFirstName(account.getFirstName());
        existedAccount.setLastName(account.getLastName());
        existedAccount.setImageUrl(account.getImageUrl());

        accountRepository.save(existedAccount);
        return existedAccount;
    }


    /**
     * Google 토큰 검증
     */
    private Account verifyIdToken(String token) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(token);

            if (idTokenObj == null) {
                return null;
            }

            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");

            return Account.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .imageUrl(pictureUrl)
                    .roles("ROLE_USER")
                    .build();

        } catch (GeneralSecurityException | IOException e) {
            //throw new RuntimeException(e);
            return null;
        }
    }

    /**
     * Refresh 토큰 Cookie에 설정
     */
    public void setRefreshToken(String token, HttpServletResponse response) {
        // Refresh 토큰 cookie 설정
        final ResponseCookie cookie = ResponseCookie.from("RID", token)
                .httpOnly(true)     //  브라우저에서 쿠키로 접근 불가 (XSS 공격 차단)
                .secure(false)      //  로컬에서 하는거라 우선 false
                //.secure(true)       //  HTTPS 가 아닌 통신에서 쿠키 전송불가
                .maxAge(7 * 24 * 3600) // 7일
                .path("/")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /**
     * Refresh 토큰 인증이후 토큰 재발급
     */

    public TokenDto reissue(String id, HttpServletResponse response) {
        Long userId = Long.parseLong(id);
        Account account = accountRepository.findById(userId)
                .orElse(null);

        if (account == null) {
            throw new NoSuchDataException("계정이 존재하지 않습니다.");
        }

        String accessToken = jwtUtils.createToken(account, false);
        String refreshToken = jwtUtils.createToken(account, true);

        setRefreshToken(refreshToken, response);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
}
