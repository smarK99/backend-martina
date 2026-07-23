package com.example.inicial1.security.controller;

import com.example.inicial1.security.dto.CambioClaveDTO;
import com.example.inicial1.security.dto.UsuarioAltaDTO;
import com.example.inicial1.security.dto.UsuarioModificacionDTO;
import com.example.inicial1.security.dto.UsuarioRolDTO;
import com.example.inicial1.services.IUsuarioService; // <-- Importamos la interfaz correcta
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte sin error de CORS
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService; // <-- Inyectamos usando la 'I'

    /**
     * Obtiene todos los usuarios activos (o inactivos, según defina el servicio)
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DUEÑO')")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(usuarioService.obtenerTodos());
        } catch (Exception e) {
            // Si algo falla, atajamos la excepción y devolvemos un error 500 a Angular
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al obtener la lista de usuarios: " + e.getMessage()));
        }
    }

    /**
     * CU N°19: Alta de Usuario
     * El @Valid le dice a Spring que revise las anotaciones @NotBlank y @Email del DTO
     */
    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DUEÑO')")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioAltaDTO dto) {
        return ResponseEntity.ok(usuarioService.crear(dto));
    }

    /**
     * CU N°19 (C.A. 1): Modificación de Usuario
     */
    @PutMapping("/actualizar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DUEÑO')")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody UsuarioModificacionDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(dto));
    }

    /**
     * CU N°19 (C.A. 2): Baja Lógica de Usuario
     * Usamos un Map para atajar el JSON simple { "codUsuario": 1 } que manda Angular
     */
    @PutMapping("/baja")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DUEÑO')")
    public ResponseEntity<?> darDeBaja(@RequestBody Map<String, Long> payload) {
        Long codUsuario = payload.get("codUsuario");
        usuarioService.bajaLogica(codUsuario);
        return ResponseEntity.ok().body(Map.of("mensaje", "Usuario dado de baja exitosamente"));
    }

    /**
     * CU N°20 y N°21: Asignar y Revocar Roles
     */
    @PutMapping("/roles") // O @PostMapping("/roles"), dependiendo de cómo lo llame tu Angular
    public ResponseEntity<?> actualizarPermisosUsuario(@RequestBody UsuarioRolDTO dto) {
        try {
            // ¡ESTA ES LA LÍNEA MÁGICA QUE TE FALTABA!
            // Ahora sí el controlador llama a tu servicio y el IDE va a marcar "1 uso"
            usuarioService.actualizarRoles(dto);

            return ResponseEntity.ok("{\"mensaje\": \"Roles actualizados correctamente en BD\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/cambiar-clave")
    public ResponseEntity<?> cambiarClavePersonal(@RequestBody CambioClaveDTO dto) {
        try {
            // Magia pura: Obtenemos el username del usuario logueado directamente desde el Token JWT
            String usernameAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

            // Se lo pasamos al servicio
            usuarioService.cambiarClave(usernameAutenticado, dto);

            return ResponseEntity.ok("{\"mensaje\": \"Contraseña actualizada correctamente\"}");
        } catch (Exception e) {
            // Si la clave actual es incorrecta, devolvemos un error 400 Bad Request
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}