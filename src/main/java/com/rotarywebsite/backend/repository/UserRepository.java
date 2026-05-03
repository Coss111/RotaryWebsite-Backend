package com.rotarywebsite.backend.repository;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Compatibilidad con código existente
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Versión mejorada para búsquedas case-insensitive
    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<User> findByRol(UserRole rol);

    List<User> findByActivoTrue();

    Optional<User> findByEmailAndActivoTrue(String email);

    Optional<User> findByEmailIgnoreCaseAndActivoTrue(String email);
}