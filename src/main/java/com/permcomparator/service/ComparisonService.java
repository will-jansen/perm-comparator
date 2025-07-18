package com.permcomparator.service;

import com.permcomparator.model.PermissionSet;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ComparisonService {
    // Constants for comparison types
    private static final String USER_PERMS = "UserPerms";

    // Main entry for user permission comparison
    public String compareUserPerms(String... ids) {
        PermissionSet[] permsets = getPermsetArray(USER_PERMS, ids);
        classifyUserPerms(permsets);
        return generatePermsJson(permsets);
    }

    // Helper to create PermissionSet array (stub, needs real data)
    private PermissionSet[] getPermsetArray(String compareType, String... ids) {
        int numberOfIds = ids.length;
        PermissionSet[] permsets = new PermissionSet[numberOfIds];
        for (int i = 0; i < numberOfIds; i++) {
            // TODO: Fetch real PermissionSet data from Salesforce
            permsets[i] = new PermissionSet();
            permsets[i].setId(ids[i]);
            permsets[i].setUserPerms(new HashSet<>(Arrays.asList("PermissionsRead", "PermissionsEdit")));
        }
        return permsets;
    }

    // Classify unique, common, and difference user perms
    private void classifyUserPerms(PermissionSet[] permsets) {
        int numberOfPermsets = permsets.length;
        for (int i = 0; i < numberOfPermsets; i++) {
            if (permsets[i] != null) {
                Set<String> uniquePerms = new HashSet<>(permsets[i].getUserPerms());
                Set<String> commonPerms = new HashSet<>(permsets[i].getUserPerms());
                for (int j = 0; j < numberOfPermsets; j++) {
                    if ((permsets[j] != null) && (j != i)) {
                        uniquePerms.removeAll(permsets[j].getUserPerms());
                        commonPerms.retainAll(permsets[j].getUserPerms());
                    }
                }
                permsets[i].setUniqueUserPerms(uniquePerms);
                permsets[i].setCommonUserPerms(commonPerms);
                Set<String> differencePerms = new HashSet<>(permsets[i].getUserPerms());
                differencePerms.removeAll(commonPerms);
                permsets[i].setDifferenceUserPerms(differencePerms);
            }
        }
    }

    // Generate a simple JSON string for the comparison result
    private String generatePermsJson(PermissionSet[] permsets) {
        int numberOfPermsets = permsets.length;
        StringBuilder jsonBuild = new StringBuilder();
        jsonBuild.append("{\"numberOfPermsets\": ").append(numberOfPermsets);
        for (int i = 0; i < numberOfPermsets; i++) {
            if (permsets[i] != null) {
                jsonBuild.append(", \"permset").append(i + 1).append("_UserUnique\": ")
                        .append(permsets[i].getUniqueUserPerms())
                        .append(", \"permset").append(i + 1).append("_UserCommon\": ")
                        .append(permsets[i].getCommonUserPerms())
                        .append(", \"permset").append(i + 1).append("_UserDifferences\": ")
                        .append(permsets[i].getDifferenceUserPerms());
            }
        }
        jsonBuild.append(" }");
        return jsonBuild.toString();
    }

    // --- Object Permission Comparison ---
    private static final String OBJECT_PERMS = "ObjectPerms";

    public String compareObjectPerms(String... ids) {
        PermissionSet[] permsets = getPermsetArray(OBJECT_PERMS, ids);
        classifyObjectPerms(permsets);
        return generateObjectPermsJson(permsets);
    }

    private void classifyObjectPerms(PermissionSet[] permsets) {
        // Stub: In a real implementation, fill in logic for object permission comparison
        // For now, just copy user perms logic for demonstration
        classifyUserPerms(permsets);
    }

    private String generateObjectPermsJson(PermissionSet[] permsets) {
        int numberOfPermsets = permsets.length;
        StringBuilder jsonBuild = new StringBuilder();
        jsonBuild.append("{\"numberOfPermsets\": ").append(numberOfPermsets);
        for (int i = 0; i < numberOfPermsets; i++) {
            if (permsets[i] != null) {
                jsonBuild.append(", \"permset").append(i + 1).append("_ObjectUnique\": ")
                        .append(permsets[i].getUniqueUserPerms())
                        .append(", \"permset").append(i + 1).append("_ObjectCommon\": ")
                        .append(permsets[i].getCommonUserPerms())
                        .append(", \"permset").append(i + 1).append("_ObjectDifferences\": ")
                        .append(permsets[i].getDifferenceUserPerms());
            }
        }
        jsonBuild.append(" }");
        return jsonBuild.toString();
    }

    // --- Setup Entity Access Comparison ---
    private static final String SETUP_ENTITY_PERMS = "SetupEntityPerms";

    public String compareSetupEntityPerms(String... ids) {
        PermissionSet[] permsets = getPermsetArray(SETUP_ENTITY_PERMS, ids);
        classifySetupEntityPerms(permsets);
        return generateSetupEntityPermsJson(permsets);
    }

    private void classifySetupEntityPerms(PermissionSet[] permsets) {
        // Stub: In a real implementation, fill in logic for setup entity comparison
        // For now, just copy user perms logic for demonstration
        classifyUserPerms(permsets);
    }

    private String generateSetupEntityPermsJson(PermissionSet[] permsets) {
        int numberOfPermsets = permsets.length;
        StringBuilder jsonBuild = new StringBuilder();
        jsonBuild.append("{\"numberOfPermsets\": ").append(numberOfPermsets);
        for (int i = 0; i < numberOfPermsets; i++) {
            if (permsets[i] != null) {
                jsonBuild.append(", \"permset").append(i + 1).append("_SetupEntityUnique\": ")
                        .append(permsets[i].getUniqueUserPerms())
                        .append(", \"permset").append(i + 1).append("_SetupEntityCommon\": ")
                        .append(permsets[i].getCommonUserPerms())
                        .append(", \"permset").append(i + 1).append("_SetupEntityDifferences\": ")
                        .append(permsets[i].getDifferenceUserPerms());
            }
        }
        jsonBuild.append(" }");
        return jsonBuild.toString();
    }
} 