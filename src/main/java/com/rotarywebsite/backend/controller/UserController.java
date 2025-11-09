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
            return Map.of(
                "authenticated", false,
                "message", "Usuario no autenticado"
            );
        }
        
        // GitHub attributes
        String name = principal.getAttribute("name");
        String login = principal.getAttribute("login");
        String email = principal.getAttribute("email");
        String avatarUrl = principal.getAttribute("avatar_url");
        String htmlUrl = principal.getAttribute("html_url");
        
        return Map.of(
            "authenticated", true,
            "name", name != null ? name : login,
            "username", login,
            "email", email,
            "avatarUrl", avatarUrl,
            "profileUrl", htmlUrl,
            "provider", "GitHub"
        );
    }

    @GetMapping("/info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Map.of("authenticated", false);
        }
        return principal.getAttributes();
    }

    @GetMapping("/status")
    public Map<String, Object> authStatus(@AuthenticationPrincipal OAuth2User principal) {
        boolean isAuthenticated = principal != null;
        Map<String, Object> response = Map.of(
            "authenticated", isAuthenticated,
            "timestamp", System.currentTimeMillis()
        );
        
        if (isAuthenticated) {
            return Map.of(
                "authenticated", true,
                "user", principal.getAttribute("login"),
                "name", principal.getAttribute("name")
            );
        }
        
        return response;
    }
}