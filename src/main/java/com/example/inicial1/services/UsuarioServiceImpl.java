package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaUsuarioDTO;
import com.example.inicial1.dtos.UsuarioTUDTO;
import com.example.inicial1.entities.TipoUsuario;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.repositories.TipoUsuarioRepository;
import com.example.inicial1.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario,Long> implements IUsuarioService {

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Usuario crearUsuario(AltaUsuarioDTO altaUsuarioDTO) throws Exception {
        try{
            //Buscamos primero el tipo usuario recibido
            TipoUsuario tu = tipoUsuarioRepository.findById(altaUsuarioDTO.getIdTipoUsuario()).orElseThrow(() -> new RuntimeException("TipoUsuario no existe"));

            //Creamos el nuevo usuario
            Usuario usuario = Usuario.builder()
                    .username(altaUsuarioDTO.getUsername())
                    .password(altaUsuarioDTO.getPassword())
                    .nombreCompletoUsuario(altaUsuarioDTO.getNombreCompletoUsuario())
                    .dni(altaUsuarioDTO.getDni())
                    .email(altaUsuarioDTO.getEmail())
                    .telefono(altaUsuarioDTO.getTelefono())
                    .direccion(altaUsuarioDTO.getDireccion())
                    .fechaHoraAltaUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaUsuario(null)
                    .tipoUsuarioList(new ArrayList<>())
                    .build();

            //Agregamos a la lista de TU al tipo usuario recibido
            usuario.getTipoUsuarioList().add(tu);

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional
    public Usuario asignarRolUsuario(UsuarioTUDTO utudto) throws Exception {
        try{
            //Validamos la existencia del TipoUsuario
            if(tipoUsuarioRepository.existsById(utudto.getIdTipoUsuario()) && usuarioRepository.existsById(utudto.getIdUsuario())){

                Usuario usuario = usuarioRepository.findById(utudto.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                List<TipoUsuario> tipoUsuarioList = usuario.getTipoUsuarioList();
                tipoUsuarioList.add(tipoUsuarioRepository.findById(utudto.getIdTipoUsuario()).orElseThrow(() -> new RuntimeException("Tipo usuario no encontrado")));

                return usuarioRepository.save(usuario);
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarUsuario(Long idUsuario) throws Exception {
        try{
            if(usuarioRepository.existsById(idUsuario)){
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                usuario.setFechaHoraBajaUsuario(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                usuarioRepository.save(usuario);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
