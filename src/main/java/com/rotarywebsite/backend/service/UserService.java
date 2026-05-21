package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(String email, String password, UserRole rol) {
        String normalizedEmail = email.trim().toLowerCase();

        if (usuarioRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User usuario = new User(normalizedEmail, passwordEncoder.encode(password), rol);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<User> getById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    @Transactional
    public void updateLastLogin(Long usuarioId) {
        User usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<User> getByRole(UserRole rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Transactional
    public User changeStatus(Long usuarioId, boolean activo) {
        User usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(activo);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getActiveUsers() {
        return usuarioRepository.findByActivoTrue();
    }

    // Mantiene el método nuevo de José para habilitar la edición de perfiles en el frontend
    @Transactional
    public User updateUser(Long userId, String email, String password, UserRole role, Boolean active) {
        User usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (email != null && !email.isBlank()) {
            String normalizedEmail = email.trim().toLowerCase();

            if (!normalizedEmail.equalsIgnoreCase(usuario.getEmail())
                    && usuarioRepository.existsByEmailIgnoreCase(normalizedEmail)) {
                throw new RuntimeException("El email ya está registrado");
            }

            usuario.setEmail(normalizedEmail);
        }

        if (password != null && !password.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }

        if (role != null) {
            usuario.setRol(role);
        }

        if (active != null) {
            usuario.setActivo(active);
        }

        return usuarioRepository.save(usuario);
    }
}