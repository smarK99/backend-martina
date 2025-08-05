package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaUsuarioDTO;
import com.example.inicial1.dtos.RevocarRolUsuarioDTO;
import com.example.inicial1.dtos.UsuarioTUDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Usuario;

import java.util.List;

public interface IUsuarioService extends BaseService<Usuario,Long>{

    public List<Usuario> obtenerTodos() throws Exception;
    public Usuario crearUsuario(AltaUsuarioDTO altaUsuarioDTO) throws Exception;
    public Usuario asignarRolUsuario(UsuarioTUDTO utudto) throws Exception;
    public Boolean revocarRolUsuario(RevocarRolUsuarioDTO revocarRolUsuarioDTO) throws Exception;
    public Boolean borrarUsuario(Long idUsuario) throws Exception;
}
