package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar usuario por email
    Optional<User> findByEmail(String email);
    
    // Verificar si existe un usuario con ese email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por rol
    List<User> findByRol(UserRole rol);
    
    // Buscar usuarios activos
    List<User> findByActivoTrue();
    
    // Buscar usuario por email y activo
    Optional<User> findByEmailAndActivoTrue(String email);
}