package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.dto.MemberDTO;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.MembershipStatus;
import com.rotarywebsite.backend.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/miembros")
@CrossOrigin
public class MemberController {

    private final MemberService miembroService;

    public MemberController(MemberService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> obtenerTodos() {
        List<MemberDTO> miembros = miembroService.getAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(miembros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Member> miembroOpt = miembroService.getById(id);

        if (miembroOpt.isPresent()) {
            return ResponseEntity.ok(toDto(miembroOpt.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Miembro no encontrado"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MemberDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<MemberDTO> miembros = miembroService.searchByName(nombre)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(miembros);
    }

    @GetMapping("/estado/{status}")
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String status) {
        try {
            MembershipStatus membershipStatus = MembershipStatus.valueOf(status.toUpperCase());

            List<MemberDTO> miembros = miembroService.getByStatus(membershipStatus)
                    .stream()
                    .map(this::toDto)
                    .toList();

            return ResponseEntity.ok(miembros);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado de membresía inválido"));
        }
    }

    @PutMapping("/{id}/renovar")
    public ResponseEntity<?> renovarMembresia(@PathVariable Long id) {
        try {
            Member miembro = miembroService.renewMembership(id);
            return ResponseEntity.ok(toDto(miembro));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/pendientes-renovacion")
    public ResponseEntity<List<MemberDTO>> obtenerParaRenovacion() {
        List<MemberDTO> miembros = miembroService.getPendingRenewal()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(miembros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMiembro(@PathVariable Long id, @RequestBody MemberDTO dto) {
        try {
            Member miembro = miembroService.updateMember(id, dto);
            return ResponseEntity.ok(toDto(miembro));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> estadisticas = Map.of(
                "activos", miembroService.countByStatus(MembershipStatus.ACTIVE),
                "pendientes", miembroService.countByStatus(MembershipStatus.PENDING_RENEWAL),
                "inactivos", miembroService.countByStatus(MembershipStatus.INACTIVE),
                "suspendidos", miembroService.countByStatus(MembershipStatus.SUSPENDED)
        );

        return ResponseEntity.ok(estadisticas);
    }

    private MemberDTO toDto(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getNombre());
        dto.setPhone(member.getTelefono());
        dto.setOccupation(member.getOcupacion());
        dto.setAddress(member.getDireccion());
        dto.setMembershipStatus(member.getEstadoMembresia());
        dto.setJoinDate(member.getFechaIngreso());
        dto.setRenewalDate(member.getFechaRenovacion());

        if (member.getUsuario() != null) {
            dto.setUserId(member.getUsuario().getId());
            dto.setEmail(member.getUsuario().getEmail());
            dto.setActive(member.getUsuario().getActivo());
        }

        return dto;
    }
}