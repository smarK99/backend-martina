package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.entities.Categoria;

import java.util.List;

public interface ICategoriaService extends BaseService<Categoria,Long> {

    public List<Categoria> obtenerTodos() throws Exception;
    public Categoria crearCategoria(AltaCategoriaDTO altaCategoriaDTO) throws Exception;
    public Boolean borrarCategoria(Long id) throws Exception;
}
