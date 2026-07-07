package com.rifas.v1.backend_rifas.repository;

import com.rifas.v1.backend_rifas.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    // 1. Buscar todos los boletos vendidos/apartados de una rifa específica
    List<Boleto> findByRifaId(Long rifaId);

    // 2. Buscar todos los boletos que le pertenecen a un usuario
    List<Boleto> findByUsuarioEmail(String email);
    
    // 3. Verificar si un número específico de boleto ya fue vendido en una rifa
    boolean existsByRifaIdAndNumeroBoleto(Long rifaId, Integer numeroBoleto);
}