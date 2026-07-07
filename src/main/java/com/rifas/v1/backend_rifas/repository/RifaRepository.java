package com.rifas.v1.backend_rifas.repository;

import com.rifas.v1.backend_rifas.model.Rifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RifaRepository extends JpaRepository<Rifa, Long> {
    // Aquí Spring Data JPA nos regala automáticamente métodos como:
    // save(), findAll(), findById(), deleteById()
    
    // Podemos agregar una consulta personalizada por si queremos filtrar en el futuro
    List<Rifa> findByEstado(String estado);
}
