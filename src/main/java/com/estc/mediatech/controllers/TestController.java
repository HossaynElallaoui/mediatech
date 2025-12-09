package com.estc.mediatech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.ok("Public endpoint works!");
    }

    @GetMapping("/secured")
    public ResponseEntity<?> securedEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Secured endpoint works!");
        response.put("username", auth != null ? auth.getName() : "none");
        response.put("authorities", auth != null ? auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(", ")) : "none");

        return ResponseEntity.ok(response);
    }
}
