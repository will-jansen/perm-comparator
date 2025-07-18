package com.permcomparator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.permcomparator.dto.SalesforceUser;
import com.permcomparator.dto.SalesforcePermissionSet;
import com.permcomparator.dto.SalesforceProfile;
import java.util.ArrayList;

@Service
public class SalesforceService {
    @Value("${salesforce.client-id}")
    private String clientId;
    @Value("${salesforce.client-secret}")
    private String clientSecret;
    @Value("${salesforce.redirect-uri}")
    private String redirectUri;

    private static final String API_VERSION = "v60.0";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Placeholder for now; will be replaced by session-based values after OAuth2
    private String getAccessToken() {
        return System.getenv("SALESFORCE_ACCESS_TOKEN");
    }
    private String getInstanceUrl() {
        return System.getenv("SALESFORCE_INSTANCE_URL");
    }

    // Example method to run a SOQL query
    public ResponseEntity<String> query(String instanceUrl, String accessToken, String soql) {
        String url = String.format("%s/services/data/%s/query/?q=%s", instanceUrl, API_VERSION, soql);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    // Example method to describe an object
    public ResponseEntity<String> describeObject(String instanceUrl, String accessToken, String objectName) {
        String url = String.format("%s/services/data/%s/sobjects/%s/describe/", instanceUrl, API_VERSION, objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public List<SalesforceUser> fetchUsers(String search, String accessToken, String instanceUrl) {
        String soql = "SELECT Id, Name FROM User WHERE IsActive=true ORDER BY Name LIMIT 100";
        List<SalesforceUser> users = new ArrayList<>();
        try {
            ResponseEntity<String> response = query(instanceUrl, accessToken, soql);
            JsonNode root = objectMapper.readTree(response.getBody());
            for (JsonNode record : root.path("records")) {
                SalesforceUser user = new SalesforceUser();
                user.setId(record.path("Id").asText());
                user.setName(record.path("Name").asText());
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<SalesforcePermissionSet> fetchPermissionSets(String search, String accessToken, String instanceUrl) {
        String soql = "SELECT Id, Label FROM PermissionSet WHERE IsOwnedByProfile=false ORDER BY Label LIMIT 100";
        List<SalesforcePermissionSet> permsets = new ArrayList<>();
        try {
            ResponseEntity<String> response = query(instanceUrl, accessToken, soql);
            JsonNode root = objectMapper.readTree(response.getBody());
            for (JsonNode record : root.path("records")) {
                SalesforcePermissionSet ps = new SalesforcePermissionSet();
                ps.setId(record.path("Id").asText());
                ps.setLabel(record.path("Label").asText());
                permsets.add(ps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permsets;
    }

    public List<SalesforceProfile> fetchProfiles(String search, String accessToken, String instanceUrl) {
        String soql = "SELECT Id, Name FROM Profile ORDER BY Name LIMIT 100";
        List<SalesforceProfile> profiles = new ArrayList<>();
        try {
            ResponseEntity<String> response = query(instanceUrl, accessToken, soql);
            JsonNode root = objectMapper.readTree(response.getBody());
            for (JsonNode record : root.path("records")) {
                SalesforceProfile profile = new SalesforceProfile();
                profile.setId(record.path("Id").asText());
                profile.setName(record.path("Name").asText());
                profiles.add(profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profiles;
    }

    // TODO: Add OAuth2 flow and caching as needed
} 