package com.permcomparator.controller;

import com.permcomparator.dto.SalesforceUser;
import com.permcomparator.dto.SalesforcePermissionSet;
import com.permcomparator.dto.SalesforceProfile;
import com.permcomparator.service.SalesforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private SalesforceService salesforceService;

    @GetMapping("/users")
    public ResponseEntity<List<SalesforceUser>> getUsers(@RequestParam(required = false) String search,
        @RegisteredOAuth2AuthorizedClient("salesforce") OAuth2AuthorizedClient client,
        @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.oauth2.core.user.OAuth2User principal) {
        String accessToken = client.getAccessToken().getTokenValue();
        String instanceUrl = principal != null ? (String) principal.getAttributes().get("instance_url") : null;
        return ResponseEntity.ok(salesforceService.fetchUsers(search, accessToken, instanceUrl));
    }

    @GetMapping("/permissionsets")
    public ResponseEntity<List<SalesforcePermissionSet>> getPermissionSets(@RequestParam(required = false) String search,
        @RegisteredOAuth2AuthorizedClient("salesforce") OAuth2AuthorizedClient client,
        @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.oauth2.core.user.OAuth2User principal) {
        String accessToken = client.getAccessToken().getTokenValue();
        String instanceUrl = principal != null ? (String) principal.getAttributes().get("instance_url") : null;
        return ResponseEntity.ok(salesforceService.fetchPermissionSets(search, accessToken, instanceUrl));
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<SalesforceProfile>> getProfiles(@RequestParam(required = false) String search,
        @RegisteredOAuth2AuthorizedClient("salesforce") OAuth2AuthorizedClient client,
        @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.oauth2.core.user.OAuth2User principal) {
        String accessToken = client.getAccessToken().getTokenValue();
        String instanceUrl = principal != null ? (String) principal.getAttributes().get("instance_url") : null;
        return ResponseEntity.ok(salesforceService.fetchProfiles(search, accessToken, instanceUrl));
    }
} 
