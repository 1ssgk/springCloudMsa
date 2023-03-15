package com.msa.authservice.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper om;

    public JwtAuthenticationEntryPoint(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setHeader("dddd", "dddd");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);


    }
}
