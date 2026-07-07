package com.rifas.v1.backend_rifas.controller;

import com.rifas.v1.backend_rifas.model.Rifa;
import com.rifas.v1.backend_rifas.service.RifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rifas")
public class RifaController {

    private final RifaService rifaService;

    public RifaController(RifaService rifaService) {
        this.rifaService = rifaService;
    }

    // 1. GET: Listar todas las rifas de la BD reales
    @GetMapping
    public ResponseEntity<List<Rifa>> listarRifas() {
        List<Rifa> rifas = rifaService.obtenerTodasLasRifas();
        return ResponseEntity.ok(rifas);
    }

    // 2. POST: Crear una nueva rifa desde Postman (Útil para poblar la BD)
    @PostMapping
    public ResponseEntity<Rifa> crearRifa(@RequestBody Rifa rifa) {
        Rifa nuevaRifa = rifaService.guardarRifa(rifa);
        return ResponseEntity.ok(nuevaRifa);
    }
}
