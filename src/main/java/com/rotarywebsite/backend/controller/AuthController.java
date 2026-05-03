package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.AuthResponse;
import com.rotarywebsite.backend.dto.LoginRequest;
import com.rotarywebsite.backend.dto.RegisterRequest;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.service.JwtService;
import com.rotarywebsite.backend.service.MemberService;
import com.rotarywebsite.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          MemberService memberService,
                          AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtService jwtService) {
        this.userService = userService;
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            Optional<User> userOpt = userService.getByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
            }

            User user = userOpt.get();
            if (!user.getActivo()) {
                return ResponseEntity.status(403).body(Map.of("error", "Cuenta desactivada"));
            }

            userService.updateLastLogin(user.getId());

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);

            AuthResponse response = new AuthResponse(
                    "Login exitoso",
                    "Bearer",
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getRol().name()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(Map.of("error", "Cuenta desactivada"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

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