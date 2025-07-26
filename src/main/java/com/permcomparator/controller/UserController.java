package com.permcomparator.controller;

import com.permcomparator.dto.SalesforceUser;
import com.permcomparator.dto.SalesforcePermissionSet;
import com.permcomparator.dto.SalesforceProfile;
import com.permcomparator.service.SalesforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private SalesforceService salesforceService;

    @GetMapping("/users")
    public ResponseEntity<List<SalesforceUser>> getUsers(@RequestParam(required = false) String search,
                                                        HttpSession session) {
        String accessToken = (String) session.getAttribute("salesforce_access_token");
        String instanceUrl = (String) session.getAttribute("salesforce_instance_url");
        
        if (accessToken == null || instanceUrl == null) {
            return ResponseEntity.status(401).build();
        }
        
        return ResponseEntity.ok(salesforceService.fetchUsers(search, accessToken, instanceUrl));
    }

    @GetMapping("/permissionsets")
    public ResponseEntity<List<SalesforcePermissionSet>> getPermissionSets(@RequestParam(required = false) String search,
                                                                           HttpSession session) {
        String accessToken = (String) session.getAttribute("salesforce_access_token");
        String instanceUrl = (String) session.getAttribute("salesforce_instance_url");
        
        if (accessToken == null || instanceUrl == null) {
            return ResponseEntity.status(401).build();
        }
        
        return ResponseEntity.ok(salesforceService.fetchPermissionSets(search, accessToken, instanceUrl));
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<SalesforceProfile>> getProfiles(@RequestParam(required = false) String search,
                                                              HttpSession session) {
        String accessToken = (String) session.getAttribute("salesforce_access_token");
        String instanceUrl = (String) session.getAttribute("salesforce_instance_url");
        
        if (accessToken == null || instanceUrl == null) {
            return ResponseEntity.status(401).build();
        }
        
        return ResponseEntity.ok(salesforceService.fetchProfiles(search, accessToken, instanceUrl));
    }
} 
