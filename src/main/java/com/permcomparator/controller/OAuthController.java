package com.permcomparator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @GetMapping("/config-check")
    public ResponseEntity<?> configCheck() {
        // Check if OAuth2 client credentials are configured
        String clientId = System.getenv("SALESFORCE_CLIENT_ID");
        String clientSecret = System.getenv("SALESFORCE_CLIENT_SECRET");
        
        boolean configured = clientId != null && !clientId.trim().isEmpty() && 
                            !clientId.equals("demo") &&
                            clientSecret != null && !clientSecret.trim().isEmpty() && 
                            !clientSecret.equals("demo");
        
        return ResponseEntity.ok(Map.of("configured", configured));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
        request.logout();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
                                @AuthenticationPrincipal OAuth2User principal) {
        if (client == null || principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        
        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "principal", principal.getAttributes(),
                "accessToken", client.getAccessToken().getTokenValue(),
                "refreshToken", client.getRefreshToken() != null ? client.getRefreshToken().getTokenValue() : null,
                "instanceUrl", principal.getAttributes().get("instance_url"),
                "client", client.getClientRegistration().getRegistrationId()
        ));
    }
} 
