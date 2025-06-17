package com.example.inicial1.repositories;

import com.example.inicial1.entities.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long>{
}
