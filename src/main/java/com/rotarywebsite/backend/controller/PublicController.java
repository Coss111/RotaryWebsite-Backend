package com.rotarywebsite.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    // ... endpoints anteriores ...

    @GetMapping("/auth-info")
    public Map<String, String> authInfo() {
        return Map.of(
            "githubAuth", "/oauth2/authorization/github",
            "loginUrl", "/oauth2/authorization/github",
            "logoutUrl", "/logout"
        );
    }

    @GetMapping("/auth-error")
    public Map<String, String> authError() {
        return Map.of(
            "error", "Authentication failed",
            "message", "Hubo un problema con la autenticación"
        );
    }

    @GetMapping("/login-urls")
    public Map<String, String> loginUrls() {
        return Map.of(
            "github", "/oauth2/authorization/github"
        );
    }
}