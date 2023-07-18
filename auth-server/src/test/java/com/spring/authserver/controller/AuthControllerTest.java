package com.spring.authserver.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.authserver.dto.LoginResponseDto;
import com.spring.authserver.dto.TokenDto;
import com.spring.authserver.entity.Account;
import com.spring.authserver.service.impl.AccountServiceImpl;
import com.spring.authserver.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("OAuth 인증 성공 후 회원가입 및 로그인 성공")
    void login() throws Exception {
        //given
        LoginResponseDto result = LoginResponseDto.builder()
                .accessToken("access")
                .refreshToken("refresh")
                .name("name")
                .email("email")
                .type("type")
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put("idToken", "oauthToken");

        given(accountService.loginOAuth(any(), any())).willReturn(result);

        //when
        ResultActions actions =
                mockMvc.perform(
                                post("/login")
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsBytes(params))
                        )
                        .andDo(print())

                        //then
                        .andExpect(status().isOk())
                        // rest docs 문서 작성
                        .andDo(
                                document("login",
                                        preprocessRequest(prettyPrint()),
                                        preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("인증 및 토큰 발급")
                                                        .requestSchema(Schema.schema("login Request"))
                                                        .requestFields(
                                                                fieldWithPath("idToken").description("oauth 토큰")
                                                        )
                                                        .responseSchema(Schema.schema("login Response"))
                                                        .responseFields(
                                                                fieldWithPath("accessToken").description("Access Token"),
                                                                fieldWithPath("refreshToken").description("Refresh Token"),
                                                                fieldWithPath("name").description("이름"),
                                                                fieldWithPath("email").description("이메일"),
                                                                fieldWithPath("type").description("oauth 구분")
                                                        )
                                                        .build()
                                        )
                                )
                        );


        //then
        actions
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("토큰 재발급")
    @WithMockUser(roles = "USER")
    void reissue() throws Exception {
        //given
        TokenDto result = TokenDto.builder()
                .refreshToken("refresh_token")
                .accessToken("access_token")
                .build();

        Account account = Account.builder()
                .id(1L)
                .lastName("Kim")
                .firstName("WonSeok")
                .email("email@google.com")
                .imageUrl("imageUrl")
                .socialId("GOOGLE")
                .roles("USER")
                .build();

        String secret = "c3ViamVjdC1wcm9qZWN0LWp3dC1zZWNyZXQtaGktaS1hbS13b25zZW9rLWhzNTEyLWFsZ29yaXRobS1iYXNlNjQtZW5jb2RpbmctYWJjZGVmZy1oaWprbG1uLW9wcXI=";

        long now = (new Date()).getTime();
        Date validity = new Date(now + 3600L);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRoles());

        String refreshToken = Jwts.builder()
                .setSubject(account.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        Cookie cookie = new Cookie("RID", refreshToken);

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(1L, refreshToken, authorities);

        given(jwtUtils.verifyAndGetAuthentication(any())).willReturn(authentication);
        given(accountService.reissue(any(), any())).willReturn(result);

        //when
        mockMvc.perform(
                        get("/reissue")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(cookie)
                )
                .andDo(print())
                .andExpect(status().isOk())
                //then
                .andDo(
                        document(
                                "reissue",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .description("토큰 재발급")
                                                .responseSchema(Schema.schema("reissue Response"))
                                                .responseFields(
                                                        fieldWithPath("accessToken").description("Access Token"),
                                                        fieldWithPath("refreshToken").description("Refresh Token")
                                                )
                                                .build()
                                )
                        ));
    }
}