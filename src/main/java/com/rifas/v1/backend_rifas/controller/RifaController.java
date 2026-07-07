package com.rifas.v1.backend_rifas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rifas")
public class RifaController {

    @GetMapping
    public ResponseEntity<?> listarRifas() {
        // Datos de prueba simulados para comprobar que la seguridad te deja pasar
        List<Map<String, Object>> rifasSimuladas = List.of(
            Map.of(
                "id", 1, 
                "titulo", "Rifa de Computadora Gamer de Escritorio", 
                "precioBoleto", 50.0, 
                "estado", "ACTIVA"
            ),
            Map.of(
                "id", 2, 
                "titulo", "Rifa de iPhone 15 Pro Max", 
                "precioBoleto", 25.0, 
                "estado", "PROXIMAMENTE"
            )
        );
        
        return ResponseEntity.ok(rifasSimuladas);
    }
}
