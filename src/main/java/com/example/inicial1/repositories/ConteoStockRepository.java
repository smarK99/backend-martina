package com.example.inicial1.repositories;

import com.example.inicial1.entities.Categoria;
import com.example.inicial1.entities.ConteoStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteoStockRepository extends BaseRepository<ConteoStock, Long>{
   //Usar getALL generico
}
