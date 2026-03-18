package com.rotarywebsite.backend.config;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe un admin para no duplicarlo
        if (userRepository.findByEmail("admin@rotary.org").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@rotary.org");
            // Encriptamos la contraseña para que el login de Spring Security funcione
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRol(UserRole.ADMINISTRATOR);
            admin.setActivo(true);
            admin.setFechaRegistro(LocalDateTime.now());
            
            userRepository.save(admin);
            System.out.println("-----------------------------------------");
            System.out.println("USUARIO ADMIN CREADO: admin@rotary.org / admin123");
            System.out.println("-----------------------------------------");
        }
    }
}