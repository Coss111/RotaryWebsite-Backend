package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.MemberDTO;
import com.rotarywebsite.backend.dto.UserDTO;
import com.rotarywebsite.backend.dto.UserUpsertRequest;
import com.rotarywebsite.backend.dto.UserStatusUpdateRequest;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.MemberRepository;
import com.rotarywebsite.backend.service.MemberService;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public UserController(UserService userService,
                          MemberService memberService,
                          MemberRepository memberRepository) {
        this.userService = userService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
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

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserUpsertRequest request) {
        try {
            if (request.getRole() == null || request.getRole() == UserRole.PUBLIC) {
                return ResponseEntity.badRequest().body(Map.of("error", "El rol es obligatorio"));
            }

            User user;
            if (request.getRole() == UserRole.MEMBER) {
                if (isBlank(request.getName()) || isBlank(request.getPassword()) || isBlank(request.getEmail())) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Nombre, email y contraseña son obligatorios para miembros"));
                }

                Member member = memberService.createMember(
                        request.getName(),
                        request.getPhone(),
                        request.getOccupation(),
                        request.getEmail(),
                        request.getPassword()
                );

                if (!isBlank(request.getAddress())) {
                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setName(member.getNombre());
                    memberDTO.setPhone(member.getTelefono());
                    memberDTO.setOccupation(member.getOcupacion());
                    memberDTO.setAddress(request.getAddress());
                    memberDTO.setEmail(member.getUsuario().getEmail());
                    member = memberService.updateMember(member.getId(), memberDTO);
                }

                user = member.getUsuario();
            } else {
                if (isBlank(request.getPassword()) || isBlank(request.getEmail())) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Email y contraseña son obligatorios"));
                }

                user = userService.createUser(request.getEmail(), request.getPassword(), request.getRole());
                if (request.getActive() != null && !request.getActive()) {
                    user = userService.changeStatus(user.getId(), false);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpsertRequest request) {
        try {
            User existingUser = userService.getById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Member member = memberRepository.findByUsuarioId(id).orElse(null);

            if (request.getRole() == UserRole.PUBLIC) {
                return ResponseEntity.badRequest().body(Map.of("error", "No se permite asignar el rol PUBLIC"));
            }

            if (member != null && request.getRole() != null && request.getRole() != UserRole.MEMBER) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Un usuario con perfil de miembro no puede cambiarse a administrador desde esta pantalla"));
            }

            if (member == null && request.getRole() == UserRole.MEMBER) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Para convertir un usuario en miembro se requiere crear un perfil de miembro"));
            }

            User updatedUser = userService.updateUser(
                    id,
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole(),
                    request.getActive()
            );

            if (member != null) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setName(!isBlank(request.getName()) ? request.getName() : member.getNombre());
                memberDTO.setPhone(request.getPhone() != null ? request.getPhone() : member.getTelefono());
                memberDTO.setOccupation(request.getOccupation() != null ? request.getOccupation() : member.getOcupacion());
                memberDTO.setAddress(request.getAddress() != null ? request.getAddress() : member.getDireccion());
                memberDTO.setEmail(updatedUser.getEmail());
                memberService.updateMember(member.getId(), memberDTO);
            }

            return ResponseEntity.ok(toDto(updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
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

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}