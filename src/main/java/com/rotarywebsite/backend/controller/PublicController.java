package com.rotarywebsite.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "OK", "message", "Backend funcionando");
    }

    @GetMapping("/logout-success")
    public Map<String, String> logoutSuccess() {
        return Map.of("message", "Logout exitoso");
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("app", "Rotary Website", "version", "1.0");
    }
}