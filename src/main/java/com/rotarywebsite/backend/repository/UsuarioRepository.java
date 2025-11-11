package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.Usuario;
import com.rotarywebsite.backend.model.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);
    
    // Verificar si existe un usuario con ese email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por rol
    List<Usuario> findByRol(RolUsuario rol);
    
    // Buscar usuarios activos
    List<Usuario> findByActivoTrue();
    
    // Buscar usuario por email y activo
    Optional<Usuario> findByEmailAndActivoTrue(String email);
}