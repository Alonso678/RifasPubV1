package com.rifas.v1.backend_rifas.service;

import com.rifas.v1.backend_rifas.model.Boleto;
import com.rifas.v1.backend_rifas.model.Rifa;
import com.rifas.v1.backend_rifas.model.Usuario;
import com.rifas.v1.backend_rifas.repository.BoletoRepository;
import com.rifas.v1.backend_rifas.repository.RifaRepository;
import com.rifas.v1.backend_rifas.repository.UserRepository; // Ajusta si tu repositorio de usuarios se llama diferente
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@SuppressWarnings("null")
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final RifaRepository rifaRepository;
    private final UserRepository usuarioRepository;

    public BoletoService(BoletoRepository boletoRepository, RifaRepository rifaRepository, UserRepository usuarioRepository) {
        this.boletoRepository = boletoRepository;
        this.rifaRepository = rifaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Obtener boletos de una rifa
    public List<Boleto> obtenerBoletosPorRifa(Long rifaId) {
        return boletoRepository.findByRifaId(rifaId);
    }

    // 2. Proceso de Compra de Boleto (Transaccional para asegurar consistencia)
    @Transactional
    public Boleto comprarBoleto(Long rifaId, Integer numeroBoleto, String emailUsuario) {
        
        // Validar si la rifa existe
        Rifa rifa = rifaRepository.findById(rifaId)
                .orElseThrow(() -> new RuntimeException("Error: La rifa no existe"));

        // Validar si el usuario existe
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Error: El usuario no existe"));

        // Validar si el número de boleto ya fue vendido
        if (boletoRepository.existsByRifaIdAndNumeroBoleto(rifaId, numeroBoleto)) {
            throw new RuntimeException("Error: El boleto número " + numeroBoleto + " ya está vendido");
        }

        // Validar si quedan boletos disponibles en la rifa
        if (rifa.getBoletosDisponibles() <= 0) {
            throw new RuntimeException("Error: Ya no hay boletos disponibles para esta rifa");
        }

        // Descontar un boleto disponible de la rifa
        rifa.setBoletosDisponibles(rifa.getBoletosDisponibles() - 1);
        rifaRepository.save(rifa);

        // Crear e insertar el boleto
        Boleto nuevoBoleto = new Boleto();
        nuevoBoleto.setNumeroBoleto(numeroBoleto);
        nuevoBoleto.setRifa(rifa);
        nuevoBoleto.setUsuario(usuario);

        return boletoRepository.save(nuevoBoleto);
    }
}
