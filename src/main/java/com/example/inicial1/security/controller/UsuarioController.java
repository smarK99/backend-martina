package com.example.inicial1.security.controller;

import com.example.inicial1.entities.Usuario;
import com.example.inicial1.repositories.UsuarioRepository;
import com.example.inicial1.security.dto.CambioClaveDTO;
import com.example.inicial1.security.dto.UsuarioAltaDTO;
import com.example.inicial1.security.dto.UsuarioModificacionDTO;
import com.example.inicial1.security.dto.UsuarioRolDTO;
import com.example.inicial1.security.jwt.JwtProvider;
import com.example.inicial1.services.EmailService;
import com.example.inicial1.services.IUsuarioService; // <-- Importamos la interfaz correcta
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte sin error de CORS
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService; // <-- Inyectamos usando la 'I'
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository; // Lo inyectamos directo para la búsqueda rápida

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


    @PostMapping("/recuperar-clave")
    public ResponseEntity<?> solicitarRecuperacionClave(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            // 1. BUSCAR USUARIO POR EMAIL
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

            if (usuarioOpt.isEmpty()) {
                // RETORNO SIMULADO:
                // Por seguridad, si el correo no existe, igual le decimos "te mandamos un correo".
                // Así los hackers no pueden usar este endpoint para adivinar correos de tus clientes.
                return ResponseEntity.ok(Map.of("mensaje", "Si el correo está registrado, se enviaron las instrucciones."));
            }

            // 2. GENERAR TOKEN TEMPORAL
            // Usamos el método nuevo que dura 15 minutos
            String token = jwtProvider.generateResetToken(email);

            // 3. ARMAR EL LINK PARA ANGULAR
            // Esta ruta /reset-password la vamos a crear en Angular en el próximo paso
            String linkRecuperacion = "http://localhost:4200/reset-password?token=" + token;

            // 4. ARMAR Y ENVIAR EL CORREO
            String mensaje = "Hola,\n\n"
                    + "Recibimos una solicitud para restablecer tu contraseña en Martina Sandwichs.\n"
                    + "Hacé clic en el siguiente enlace para cambiar tu clave (válido por 15 minutos):\n\n"
                    + linkRecuperacion + "\n\n"
                    + "Si no solicitaste este cambio, podés ignorar este correo de forma segura.\n";

            emailService.enviarCorreo(email, "Recuperación de Contraseña - Martina Sandwichs", mensaje);

            return ResponseEntity.ok(Map.of("mensaje", "Si el correo está registrado, se enviaron las instrucciones."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar la solicitud"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> restablecerClave(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaClave = request.get("nuevaClave");

        try {
            // 1. Verificar que el token sea válido y no haya expirado (los 15 min)
            if (!jwtProvider.validateToken(token)) {
                return ResponseEntity.badRequest().body(Map.of("error", "El enlace es inválido o ha expirado."));
            }

            // 2. Extraer el email que guardamos adentro del token
            String email = jwtProvider.getNombreUsuarioFromToken(token);

            // 3. Buscar al usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado."));
            }

            // 4. Encriptar la nueva clave y guardarla
            Usuario usuario = usuarioOpt.get();
            usuario.setPassword(passwordEncoder.encode(nuevaClave));
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error interno al restablecer la contraseña."));
        }
    }

}