package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.model.Usuario;
import com.rotarywebsite.backend.model.EstadoMembresia;
import com.rotarywebsite.backend.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Crear nuevo miembro
    public Miembro crearMiembro(String nombre, String telefono, String ocupacion, 
                               String email, String password) {
        // Crear usuario primero
        Usuario usuario = usuarioService.crearUsuario(email, password, com.rotarywebsite.backend.model.RolUsuario.MIEMBRO);
        
        // Crear miembro
        Miembro miembro = new Miembro(nombre, telefono, ocupacion, usuario);
        return miembroRepository.save(miembro);
    }

    // Obtener todos los miembros
    public List<Miembro> obtenerTodos() {
        return miembroRepository.findAll();
    }

    // Obtener miembro por ID
    public Optional<Miembro> obtenerPorId(Long id) {
        return miembroRepository.findById(id);
    }

    // Buscar miembros por nombre
    public List<Miembro> buscarPorNombre(String nombre) {
        return miembroRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener miembros por estado de membresía
    public List<Miembro> obtenerPorEstado(EstadoMembresia estado) {
        return miembroRepository.findByEstadoMembresia(estado);
    }

    // Renovar membresía
    public Miembro renovarMembresia(Long miembroId) {
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        
        miembro.setEstadoMembresia(EstadoMembresia.ACTIVO);
        miembro.setFechaRenovacion(LocalDate.now().plusYears(1));
        
        return miembroRepository.save(miembro);
    }

    // Obtener miembros que necesitan renovación
    public List<Miembro> obtenerParaRenovacion() {
        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        return miembroRepository.findByFechaRenovacionLessThan(fechaLimite);
    }

    // Contar miembros por estado
    public long contarPorEstado(EstadoMembresia estado) {
        return miembroRepository.countByEstadoMembresia(estado);
    }
}