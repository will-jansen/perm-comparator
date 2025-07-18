package com.permcomparator.model;

import lombok.Data;
import java.util.*;

@Data
public class PermissionSet {
    private String id;
    private String name;

    public enum ObjPermCategory {
        original, unique, common, differing
    }

    public enum ObjectPermissions {
        PermissionsRead, PermissionsEdit, PermissionsCreate, PermissionsDelete,
        PermissionsViewAllRecords, PermissionsModifyAllRecords
    }

    public enum SetupEntityTypes {
        APEX_CLASS("01p", "ApexClass", "Apex Classes"),
        TABSET("02u", "AppMenuItem", "Apps"),
        CONN_APP("0H4", "AppMenuItem", "Connected Apps"),
        APEX_PAGE("066", "ApexPage", "Visualforce Pages");

        private final String prefix;
        private final String apiFieldName;
        private final String displayName;

        SetupEntityTypes(final String prefix, final String apiFieldName, final String displayName) {
            this.prefix = prefix;
            this.apiFieldName = apiFieldName;
            this.displayName = displayName;
        }

        public String getPrefix() { return this.prefix; }
        public String getApiName() { return this.apiFieldName; }
        public String getDisplayName() { return this.displayName; }
    }

    private Set<String> userPerms;
    private Set<String> uniqueUserPerms;
    private Set<String> commonUserPerms;
    private Set<String> differenceUserPerms;

    private Map<ObjPermCategory, Map<String, EnumSet<ObjectPermissions>>> objPermMap;
    private Map<ObjPermCategory, Map<SetupEntityTypes, Set<String>>> seaPermMap;
} 