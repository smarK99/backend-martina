package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaUsuarioDTO;
import com.example.inicial1.dtos.RevocarRolUsuarioDTO;
import com.example.inicial1.dtos.UsuarioTUDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.security.dto.*;

import java.util.List;

public interface IUsuarioService extends BaseService<Usuario, Long> {
    List<Usuario> obtenerTodos() throws Exception;
    Usuario crear(UsuarioAltaDTO dto);
    Usuario actualizar(UsuarioModificacionDTO dto);
    void bajaLogica(Long idUsuario);
    void actualizarRoles(UsuarioRolDTO dto);
    void cambiarClave(String username, CambioClaveDTO dto);
}
