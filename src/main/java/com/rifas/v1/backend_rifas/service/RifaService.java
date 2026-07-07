package com.rifas.v1.backend_rifas.service;

import com.rifas.v1.backend_rifas.model.Rifa;
import com.rifas.v1.backend_rifas.repository.RifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RifaService {

    private final RifaRepository rifaRepository;

    // Inyección por constructor (Buena práctica)
    public RifaService(RifaRepository rifaRepository) {
        this.rifaRepository = rifaRepository;
    }

    // 1. Obtener todas las rifas de la base de datos
    public List<Rifa> obtenerTodasLasRifas() {
        return rifaRepository.findAll();
    }

    // 2. Buscar una rifa por su ID
    public Optional<Rifa> obtenerRifaPorId(Long id) {
        return rifaRepository.findById(id);
    }

    // 3. Guardar o crear una nueva rifa
    public Rifa guardarRifa(Rifa rifa) {
        // La lógica de inicialización del estado y boletos ya la maneja el @PrePersist en la Entidad
        return rifaRepository.save(rifa);
    }
}
