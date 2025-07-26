package com.permcomparator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import com.permcomparator.service.SalesforceService;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {
    
    @Autowired
    private SalesforceService salesforceService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        try {
            String clientId = credentials.get("clientId");
            String clientSecret = credentials.get("clientSecret");
            String instanceUrl = credentials.get("instanceUrl");
            
            if (clientId == null || clientSecret == null || instanceUrl == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required credentials"));
            }
            
            // Attempt OAuth flow with provided credentials
            Map<String, String> tokens = salesforceService.authenticate(clientId, clientSecret, instanceUrl);
            
            // Store credentials and tokens in session
            session.setAttribute("salesforce_client_id", clientId);
            session.setAttribute("salesforce_client_secret", clientSecret);
            session.setAttribute("salesforce_instance_url", instanceUrl);
            session.setAttribute("salesforce_access_token", tokens.get("access_token"));
            session.setAttribute("salesforce_refresh_token", tokens.get("refresh_token"));
            
            return ResponseEntity.ok(Map.of("message", "Login successful", "instanceUrl", instanceUrl));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        String accessToken = (String) session.getAttribute("salesforce_access_token");
        String instanceUrl = (String) session.getAttribute("salesforce_instance_url");
        
        if (accessToken == null || instanceUrl == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        
        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "instanceUrl", instanceUrl,
                "hasAccessToken", true
        ));
    }
} 
