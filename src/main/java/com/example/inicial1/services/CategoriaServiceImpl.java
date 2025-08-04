package com.example.inicial1.services;

import com.example.inicial1.dtos.AltaCategoriaDTO;
import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.Usuario;
import com.example.inicial1.repositories.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria,Long> implements ICategoriaService{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Transactional
    @Override
    public List<Categoria> obtenerTodos() throws Exception {
        try {
            return categoriaRepository.obtenerTodos();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Categoria crearCategoria(AltaCategoriaDTO altaCategoriaDTO) throws Exception {

        try{
            Categoria categoria = Categoria.builder()
                    .nombreCategoria(altaCategoriaDTO.getNombreCategoria())
                    .descripcionCategoria(altaCategoriaDTO.getDescripcionCategoria())
                    .fechaHoraAltaCategoria(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .fechaHoraBajaCategoria(null)
                    .build();

            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean borrarCategoria(Long id) throws Exception {
        try{
            if(categoriaRepository.existsById(id)){
                Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrado"));
                categoria.setFechaHoraBajaCategoria(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                categoriaRepository.save(categoria);
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
