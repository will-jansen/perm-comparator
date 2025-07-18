package com.permcomparator.controller;

import com.permcomparator.model.PermissionSet;
import com.permcomparator.service.SalesforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permissionsets")
public class PermissionSetController {
    @Autowired
    private SalesforceService salesforceService;

    @GetMapping
    public List<PermissionSet> getPermissionSets() {
        // TODO: Call salesforceService to fetch permission sets
        return List.of();
    }
} 