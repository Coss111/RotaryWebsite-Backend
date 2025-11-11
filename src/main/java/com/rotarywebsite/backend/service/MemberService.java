package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.MembershipStatus;
import com.rotarywebsite.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository miembroRepository;

    @Autowired
    private UserService usuarioService;

    // Crear nuevo miembro
    public Member crearMiembro(String nombre, String telefono, String ocupacion, 
                               String email, String password) {
        // Crear usuario primero
        User usuario = usuarioService.crearUsuario(email, password, com.rotarywebsite.backend.model.UserRole.MEMBER);
        
        // Crear miembro
        Member miembro = new Member(nombre, telefono, ocupacion, usuario);
        return miembroRepository.save(miembro);
    }

    // Obtener todos los miembros
    public List<Member> obtenerTodos() {
        return miembroRepository.findAll();
    }

    // Obtener miembro por ID
    public Optional<Member> obtenerPorId(Long id) {
        return miembroRepository.findById(id);
    }

    // Buscar miembros por nombre
    public List<Member> buscarPorNombre(String nombre) {
        return miembroRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener miembros por estado de membresía
    public List<Member> obtenerPorEstado(MembershipStatus estado) {
        return miembroRepository.findByEstadoMembresia(estado);
    }

    // Renovar membresía
    public Member renovarMembresia(Long miembroId) {
        Member miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        
        miembro.setEstadoMembresia(MembershipStatus.ACTIVE);
        miembro.setFechaRenovacion(LocalDate.now().plusYears(1));
        
        return miembroRepository.save(miembro);
    }

    // Obtener miembros que necesitan renovación
    public List<Member> obtenerParaRenovacion() {
        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        return miembroRepository.findByFechaRenovacionLessThan(fechaLimite);
    }

    // Contar miembros por estado
    public long contarPorEstado(MembershipStatus estado) {
        return miembroRepository.countByEstadoMembresia(estado);
    }
}