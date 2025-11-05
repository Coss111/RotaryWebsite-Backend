package com.rotarywebsite.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> userProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("error", "User not authenticated");
        }
        
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal) {
        return principal != null ? principal.getAttributes() : 
            Collections.singletonMap("error", "Not authenticated");
    }
}