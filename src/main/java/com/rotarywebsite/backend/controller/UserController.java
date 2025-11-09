package com.rotarywebsite.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> userProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("error", "User not authenticated");
        }
        
        return Map.of(
            "name", principal.getAttribute("name"),
            "email", principal.getAttribute("email"),
            "picture", principal.getAttribute("picture"),
            "authenticated", true
        );
    }

    @GetMapping("/info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal) {
        return principal != null ? principal.getAttributes() : 
            Collections.singletonMap("error", "Not authenticated");
    }

    // Endpoint público para verificar si el usuario está autenticado
    @GetMapping("/status")
    public Map<String, Object> authStatus(@AuthenticationPrincipal OAuth2User principal) {
        return Map.of(
            "authenticated", principal != null,
            "user", principal != null ? principal.getAttribute("name") : null
        );
    }
}   