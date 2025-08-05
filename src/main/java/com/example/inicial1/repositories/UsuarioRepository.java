package com.example.inicial1.repositories;

import com.example.inicial1.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario,Long>{
    @Query(
            value = "SELECT * FROM usuario WHERE fecha_hora_baja_usuario IS NULL",
            nativeQuery = true
    )
    public List<Usuario> obtenerTodos();

}
