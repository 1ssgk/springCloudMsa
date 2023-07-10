package com.spring.authserver.util;

import com.spring.authserver.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static final long TOKEN_VALIDITY = 3600 * 30L;
    private static final long TOKEN_VALIDITY_REMEMBER = 2592000000L;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 토큰 생성
     */
    public String createToken(Account account, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity = rememberMe ? new Date(now + TOKEN_VALIDITY_REMEMBER) : new Date(now + TOKEN_VALIDITY);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",account.getRoles());

        return Jwts.builder()
                .setSubject(account.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰 검증
     */
    public Authentication verifyAndGetAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("role", String.class));
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
        } catch (JwtException | IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Refresh 토큰 Cookie 설정
     */
    public void setRefreshToken(String token, HttpServletResponse response) {


    }

}
