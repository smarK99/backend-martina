package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaTipoUsuarioDTO;
import com.example.inicial1.entities.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioService extends BaseService<TipoUsuario,Long>{

    public List<TipoUsuario> obtenerTodos() throws Exception;
    public TipoUsuario crearTipoUsuario(AltaTipoUsuarioDTO altaTipoUsuarioDTO) throws Exception;
    public Boolean borrarTipoUsuario(Long id) throws Exception;
}
