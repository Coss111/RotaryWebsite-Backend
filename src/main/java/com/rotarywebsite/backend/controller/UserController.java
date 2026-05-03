package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.UserDTO;
import com.rotarywebsite.backend.dto.UserStatusUpdateRequest;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/all"})
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        List<UserDTO> users = userService.getActiveUsers()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.getById(id);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(toDto(userOpt.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> userOpt = userService.getByEmail(email);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(toDto(userOpt.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado"));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());

            List<UserDTO> users = userService.getByRole(userRole)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Rol inválido"));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long id,
                                              @RequestBody UserStatusUpdateRequest request) {
        try {
            if (request.getActive() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El campo active es obligatorio"));
            }

            User user = userService.changeStatus(id, request.getActive());
            return ResponseEntity.ok(toDto(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRol());
        dto.setActive(user.getActivo());
        dto.setRegistrationDate(user.getFechaRegistro());
        dto.setLastLogin(user.getUltimoLogin());
        return dto;
    }
}