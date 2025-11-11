package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.LoginRequest;
import com.rotarywebsite.backend.dto.RegisterRequest;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.service.UserService;
import com.rotarywebsite.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    // Endpoint para registro usando DTO
    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody RegisterRequest registerRequest) {
        try {
            Member member = memberService.createMember(
                registerRequest.getName(),
                registerRequest.getPhone(),
                registerRequest.getOccupation(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Member registered successfully");
            response.put("memberId", member.getId());
            response.put("email", registerRequest.getEmail());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint para login usando DTO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOpt = userService.getByEmail(loginRequest.getEmail());
            
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }

            User user = userOpt.get();
            userService.updateLastLogin(user.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("role", user.getRol());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Verificar si email está disponible
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String email) {
        try {
            Optional<User> user = userService.getByEmail(email);
            Map<String, Boolean> response = new HashMap<>();
            response.put("available", user.isEmpty());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}