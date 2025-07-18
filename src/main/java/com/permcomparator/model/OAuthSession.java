package com.permcomparator.model;

import lombok.Data;

@Data
public class OAuthSession {
    private String uid;
    private String idURL;
    private String accessToken;
    private String instanceUrl;
    private String signature;
} 