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
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        
        // 1. Validar que el usuario existe
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
        }

        User user = userOpt.get();

        // 2. COMPARAR CONTRASEÑA ENCRIPTADA (Crucial para seguridad)
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
        }

        // 3. Validar si el usuario está activo
        if (!user.getActivo()) {
            return ResponseEntity.status(403).body(Map.of("error", "Cuenta desactivada"));
        }

        userService.updateLastLogin(user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("userId", user.getId());
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