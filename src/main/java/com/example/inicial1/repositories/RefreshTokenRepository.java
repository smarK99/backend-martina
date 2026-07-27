package com.example.inicial1.repositories;

import com.example.inicial1.entities.RefreshToken;
import com.example.inicial1.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {

    // Sirve para buscar el token en la base de datos cuando Angular nos lo mande
    Optional<RefreshToken> findByToken(String token);

    // Sirve para borrar el token viejo si el usuario vuelve a iniciar sesión
    @Modifying
    void deleteByUsuario(Usuario usuario);
}