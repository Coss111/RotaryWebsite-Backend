package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear nuevo usuario
    public User crearUsuario(String email, String password, UserRole rol) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User usuario = new User(email, passwordEncoder.encode(password), rol);
        return usuarioRepository.save(usuario);
    }

    // Obtener usuario por ID
    public Optional<User> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<User> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Actualizar último login
    public void actualizarUltimoLogin(Long usuarioId) {
        User usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    // Listar usuarios por rol
    public List<User> listarPorRol(UserRole rol) {
        return usuarioRepository.findByRol(rol);
    }

    // Cambiar estado de usuario
    public User cambiarEstado(Long usuarioId, boolean activo) {
        User usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(activo);
        return usuarioRepository.save(usuario);
    }
}