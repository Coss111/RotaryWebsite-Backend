package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/miembros")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService miembroService;

    // Obtener todos los miembros
    @GetMapping
    public ResponseEntity<List<Member>> obtenerTodos() {
        List<Member> miembros = miembroService.getAll();
        return ResponseEntity.ok(miembros);
    }

    // Obtener miembro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<Member> miembro = miembroService.getById(id);
            if (miembro.isPresent()) {
                return ResponseEntity.ok(miembro.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Buscar miembros por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Member>> buscarPorNombre(@RequestParam String nombre) {
        List<Member> miembros = miembroService.searchByName(nombre);
        return ResponseEntity.ok(miembros);
    }

    // Renovar membresía
    @PutMapping("/{id}/renovar")
    public ResponseEntity<?> renovarMembresia(@PathVariable Long id) {
        try {
            Member miembro = miembroService.renewMembership(id);
            return ResponseEntity.ok(miembro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener miembros que necesitan renovación
    @GetMapping("/pendientes-renovacion")
    public ResponseEntity<List<Member>> obtenerParaRenovacion() {
        List<Member> miembros = miembroService.getPendingRenewal();
        return ResponseEntity.ok(miembros);
    }

    // Actualizar información de miembro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMiembro(@PathVariable Long id, @RequestBody Member miembroActualizado) {
        try {
            Optional<Member> miembroExistente = miembroService.getById(id);
            if (miembroExistente.isPresent()) {
                Member miembro = miembroExistente.get();
                miembro.setNombre(miembroActualizado.getNombre());
                miembro.setTelefono(miembroActualizado.getTelefono());
                miembro.setOcupacion(miembroActualizado.getOcupacion());
                miembro.setDireccion(miembroActualizado.getDireccion());
                
                // Aquí deberías llamar al servicio para guardar
                return ResponseEntity.ok(miembro);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener estadísticas de miembros
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> estadisticas = Map.of(
            "activos", miembroService.countByStatus(com.rotarywebsite.backend.model.MembershipStatus.ACTIVE),
            "pendientes", miembroService.countByStatus(com.rotarywebsite.backend.model.MembershipStatus.PENDING_RENEWAL),
            "inactivos", miembroService.countByStatus(com.rotarywebsite.backend.model.MembershipStatus.INACTIVE)
        );
        return ResponseEntity.ok(estadisticas);
    }
}