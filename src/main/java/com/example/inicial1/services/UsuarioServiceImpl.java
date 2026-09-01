package com.example.inicial1.services;

import com.example.inicial1.security.dto.CambioClaveDTO;
import com.example.inicial1.security.dto.UsuarioAltaDTO;
import com.example.inicial1.security.dto.UsuarioModificacionDTO;
import com.example.inicial1.security.dto.UsuarioRolDTO;
import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.repositories.TipoUsuarioRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements IUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyectamos el encriptador de contraseñas de Spring Security
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public List<Usuario> obtenerTodos() {
        try {
            return usuarioRepository.obtenerTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    /**
     * CU N°19: Alta de Usuario
     */
    @Transactional
    public Usuario crear(UsuarioAltaDTO dto) {
        try {
            // Verificamos que el usuario o email no existan previamente (regla de negocio)
            // if (usuarioRepository.existsByUsername(dto.getUsuario())) { throw new RuntimeException("El usuario ya existe"); }

            Usuario usuario = Usuario.builder()
                    .username(dto.getUsuario()) // Mapeamos el DTO a la Entidad
                    .password(passwordEncoder.encode(dto.getClaveUsuario())) // ¡Contraseña encriptada!
                    .nombreCompletoUsuario(dto.getNombreApellido())
                    .email(dto.getMailUsuario())
                    .telefono(dto.getTelefonoUsuario())
                    .direccion(dto.getDomicilioUsuario())
                    .fechaHoraAltaUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaUsuario(null)
                    .tiposUsuario(new ArrayList<>())
                    .build();

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * CU N°19 (C.A. 1): Modificación de Usuario
     */
    @Transactional
    public Usuario actualizar(UsuarioModificacionDTO dto) {
        try {
            Usuario usuario = usuarioRepository.findById(dto.getCodUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Actualizamos solo los campos permitidos
            usuario.setNombreCompletoUsuario(dto.getNombreApellido());
            usuario.setEmail(dto.getMailUsuario());
            usuario.setTelefono(dto.getTelefonoUsuario());
            usuario.setDireccion(dto.getDomicilioUsuario());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    /**
     * CU N°19 (C.A. 2): Baja Lógica
     */
    @Transactional
    public void bajaLogica(Long idUsuario) {
        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            usuario.setFechaHoraBajaUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al dar de baja el usuario: " + e.getMessage());
        }
    }

    /**
     * CU N°20 y 21 unificados: Actualización de Roles (Permisos)
     */
    @Transactional
    public void actualizarRoles(UsuarioRolDTO dto) {
        try {
            System.out.println(">>> DEBUG [Servicio]: Iniciando actualización de roles...");
            System.out.println(">>> DEBUG [Servicio]: ID del usuario: " + dto.getCodUsuario());
            System.out.println(">>> DEBUG [Servicio]: Roles recibidos desde Angular: " + dto.getRoles());

            Usuario usuario = usuarioRepository.findById(dto.getCodUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificamos que la lista no sea nula para evitar errores
            if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
                System.out.println(">>> DEBUG [Servicio]: ¡ALERTA! La lista de roles llegó vacía o nula desde Angular.");
                usuario.setTiposUsuario(new ArrayList<>());
            } else {
                List<TipoUsuario> nuevosRoles = tipoUsuarioRepository.findByNombreTipoUsuarioIn(dto.getRoles());
                System.out.println(">>> DEBUG [Servicio]: Roles encontrados en la Base de Datos: " + nuevosRoles.size());

                for(TipoUsuario tu : nuevosRoles) {
                    System.out.println("    - Encontrado: " + tu.getNombreTipoUsuario());
                }

                usuario.setTiposUsuario(nuevosRoles);
            }

            usuarioRepository.save(usuario);
            System.out.println(">>> DEBUG [Servicio]: Guardado exitoso en Hibernate.");

        } catch (Exception e) {
            System.out.println(">>> DEBUG [Servicio]: ERROR FATAL - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar los roles: " + e.getMessage());
        }
    }

    /**
     * Cambio de Contraseña Personal
     */
    @Transactional
    public void cambiarClave(String username, CambioClaveDTO dto) {
        try {
            // Buscamos al usuario por su username
            // Asume que tenés un método findByUsername en tu repositorio
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // 1. Verificamos que la clave actual ingresada coincida con la de la base de datos
            // passwordEncoder.matches(clave_sin_encriptar, clave_encriptada_en_bd)
            if (!passwordEncoder.matches(dto.getClaveActual(), usuario.getPassword())) {
                throw new RuntimeException("La contraseña actual es incorrecta.");
            }

            // 2. Si pasó la prueba, encriptamos la nueva y la guardamos
            usuario.setPassword(passwordEncoder.encode(dto.getNuevaClave()));
            usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ==========================================
    // NUEVO: Método para Paginación y Búsqueda
    // ==========================================
    @Transactional
    public Page<Usuario> buscarPaginadoYFiltrado(String termino, Pageable pageable) throws Exception {
        try {
            // Si el término viene nulo, lo pasamos a vacío para que busque todo
            String terminoBusqueda = (termino != null) ? termino : "";
            return usuarioRepository.buscarPaginadoYFiltrado(terminoBusqueda, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}