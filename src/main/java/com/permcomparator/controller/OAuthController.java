package com.permcomparator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {
    @GetMapping("/login")
    public ResponseEntity<?> login() {
        // Redirect handled by Spring Security at /oauth2/authorization/salesforce
        return ResponseEntity.ok(Map.of("loginUrl", "/oauth2/authorization/salesforce"));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
        request.logout();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RegisteredOAuth2AuthorizedClient("salesforce") OAuth2AuthorizedClient client,
                                @AuthenticationPrincipal OAuth2User principal) {
        if (client == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        return ResponseEntity.ok(Map.of(
                "principal", principal != null ? principal.getAttributes() : null,
                "accessToken", client.getAccessToken().getTokenValue(),
                "refreshToken", client.getRefreshToken() != null ? client.getRefreshToken().getTokenValue() : null,
                "client", client.getClientRegistration().getRegistrationId()
        ));
    }
} 