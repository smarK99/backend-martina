package com.example.inicial1.repositories;

import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.entities.TipoUsuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoUsuarioRepository extends BaseRepository<TipoUsuario,Long> {
    @Query(
            value = "SELECT * FROM tipo_usuario WHERE fecha_hora_fin_vigencia_tu IS NULL",
            nativeQuery = true
    )
    public List<TipoUsuario> obtenerTodos();
}
