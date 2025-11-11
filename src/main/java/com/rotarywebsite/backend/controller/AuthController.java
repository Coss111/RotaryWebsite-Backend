package com.rotarywebsite.backend.controller;

import com.rotarywebsite.backend.model.Usuario;
import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.service.UsuarioService;
import com.rotarywebsite.backend.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MiembroService miembroService;

    // Endpoint para registro de nuevos miembros
    @PostMapping("/registro")
    public ResponseEntity<?> registrarMiembro(@RequestBody Map<String, String> request) {
        try {
            String nombre = request.get("nombre");
            String telefono = request.get("telefono");
            String ocupacion = request.get("ocupacion");
            String email = request.get("email");
            String password = request.get("password");

            Miembro miembro = miembroService.crearMiembro(nombre, telefono, ocupacion, email, password);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Miembro registrado exitosamente");
            response.put("miembroId", miembro.getId());
            response.put("email", email);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint para login (cuando tengas seguridad configurada)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(email);
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
            }

            Usuario usuario = usuarioOpt.get();
            
            // Aquí iría la lógica de autenticación con Spring Security
            // Por ahora solo simulamos login exitoso
            usuarioService.actualizarUltimoLogin(usuario.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Login exitoso");
            response.put("usuarioId", usuario.getId());
            response.put("email", usuario.getEmail());
            response.put("rol", usuario.getRol());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Verificar si email está disponible
    @GetMapping("/verificar-email")
    public ResponseEntity<?> verificarEmail(@RequestParam String email) {
        try {
            Optional<Usuario> usuario = usuarioService.obtenerPorEmail(email);
            Map<String, Boolean> response = new HashMap<>();
            response.put("disponible", usuario.isEmpty());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}