package com.msa.userservice.global.provider;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;

public enum CommonOAuth2Provider {
//    GOOGLE {
//        public ClientRegistration.Builder getBuilder(String registrationId) {
//            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//            builder.scope(new String[]{"profile", "email"});
//            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
//            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
//            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
//            builder.userNameAttributeName("sub");
//            builder.clientName("Google");
//            return builder;
//        }
//    };
//
//    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";
//
//    protected final ClientRegistration.Builder getBuilder( String registrationId, ClientAuthenticationMethod method, String redirectUri) {
//        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
//        builder.clientAuthenticationMethod(method);
//        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
//        builder.redirectUri(redirectUri);
//        return builder;
//    }
//
//    public abstract ClientRegistration.Builder getBuilder(String registrationId);

    GOOGLE {

        @Override
        public Builder getBuilder(String registrationId) {

            ClientRegistration.Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);

            builder.scope("profile", "email"); //"openid"
            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
            builder.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs");
            builder.issuerUri("https://accounts.google.com");
            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
            builder.userNameAttributeName(IdTokenClaimNames.SUB);
            builder.clientName("Google");

            return builder;
        }

    },

    GITHUB {

        @Override
        public Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope("read:user");
            builder.authorizationUri("https://github.com/login/oauth/authorize");
            builder.tokenUri("https://github.com/login/oauth/access_token");
            builder.userInfoUri("https://api.github.com/user");
            builder.userNameAttributeName("id");
            builder.clientName("GitHub");
            return builder;
        }

    };

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";
    //private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/{registrationId}/login";


    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                                          String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);

        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }

    /**
     * Create a new
     * {@link org.springframework.security.oauth2.client.registration.ClientRegistration.Builder
     * ClientRegistration.Builder} pre-configured with provider defaults.
     * @param registrationId the registration-id used with the new builder
     * @return a builder instance
     */
    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
