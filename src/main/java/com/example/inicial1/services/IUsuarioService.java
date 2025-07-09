package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaUsuarioDTO;
import com.example.inicial1.dtos.UsuarioTUDTO;
import com.example.inicial1.entities.Usuario;

public interface IUsuarioService extends BaseService<Usuario,Long>{

    public Usuario crearUsuario(AltaUsuarioDTO altaUsuarioDTO) throws Exception;
    public Usuario asignarRolUsuario(UsuarioTUDTO utudto) throws Exception;
    public Boolean borrarUsuario(Long idUsuario) throws Exception;
}
