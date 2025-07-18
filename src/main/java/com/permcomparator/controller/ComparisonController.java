package com.permcomparator.controller;

import com.permcomparator.service.ComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/compare")
public class ComparisonController {
    @Autowired
    private ComparisonService comparisonService;

    @GetMapping("/user")
    public ResponseEntity<String> compareUserPerms(@RequestParam String id1, @RequestParam(required = false) String id2,
                                                   @RequestParam(required = false) String id3, @RequestParam(required = false) String id4) {
        return ResponseEntity.ok(comparisonService.compareUserPerms(id1, id2, id3, id4));
    }

    @GetMapping("/object")
    public ResponseEntity<String> compareObjectPerms(@RequestParam String id1, @RequestParam(required = false) String id2,
                                                     @RequestParam(required = false) String id3, @RequestParam(required = false) String id4) {
        return ResponseEntity.ok(comparisonService.compareObjectPerms(id1, id2, id3, id4));
    }

    @GetMapping("/setupentity")
    public ResponseEntity<String> compareSetupEntityPerms(@RequestParam String id1, @RequestParam(required = false) String id2,
                                                          @RequestParam(required = false) String id3, @RequestParam(required = false) String id4) {
        return ResponseEntity.ok(comparisonService.compareSetupEntityPerms(id1, id2, id3, id4));
    }
} 