package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/miembros")
@CrossOrigin(origins = "*")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    // Obtener todos los miembros
    @GetMapping
    public ResponseEntity<List<Miembro>> obtenerTodos() {
        List<Miembro> miembros = miembroService.obtenerTodos();
        return ResponseEntity.ok(miembros);
    }

    // Obtener miembro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<Miembro> miembro = miembroService.obtenerPorId(id);
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
    public ResponseEntity<List<Miembro>> buscarPorNombre(@RequestParam String nombre) {
        List<Miembro> miembros = miembroService.buscarPorNombre(nombre);
        return ResponseEntity.ok(miembros);
    }

    // Renovar membresía
    @PutMapping("/{id}/renovar")
    public ResponseEntity<?> renovarMembresia(@PathVariable Long id) {
        try {
            Miembro miembro = miembroService.renovarMembresia(id);
            return ResponseEntity.ok(miembro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener miembros que necesitan renovación
    @GetMapping("/pendientes-renovacion")
    public ResponseEntity<List<Miembro>> obtenerParaRenovacion() {
        List<Miembro> miembros = miembroService.obtenerParaRenovacion();
        return ResponseEntity.ok(miembros);
    }

    // Actualizar información de miembro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembroActualizado) {
        try {
            Optional<Miembro> miembroExistente = miembroService.obtenerPorId(id);
            if (miembroExistente.isPresent()) {
                Miembro miembro = miembroExistente.get();
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
            "activos", miembroService.contarPorEstado(com.rotarywebsite.backend.model.EstadoMembresia.ACTIVO),
            "pendientes", miembroService.contarPorEstado(com.rotarywebsite.backend.model.EstadoMembresia.PENDIENTE_RENOVACION),
            "inactivos", miembroService.contarPorEstado(com.rotarywebsite.backend.model.EstadoMembresia.INACTIVO)
        );
        return ResponseEntity.ok(estadisticas);
    }
}