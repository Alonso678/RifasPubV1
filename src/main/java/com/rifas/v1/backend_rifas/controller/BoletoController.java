package com.rifas.v1.backend_rifas.controller;

import com.rifas.v1.backend_rifas.model.Boleto;
import com.rifas.v1.backend_rifas.service.BoletoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    // 1. GET: Obtener todos los boletos vendidos/ocupados de una rifa específica
    @GetMapping("/rifa/{rifaId}")
    public ResponseEntity<List<Boleto>> listarBoletosPorRifa(@PathVariable Long rifaId) {
        List<Boleto> boletos = boletoService.obtenerBoletosPorRifa(rifaId);
        return ResponseEntity.ok(boletos);
    }

    // 2. POST: Comprar un boleto de forma segura
    @PostMapping("/comprar")
    public ResponseEntity<?> comprarBoleto(@RequestBody Map<String, Object> payload, Authentication authentication) {
        try {
            // Extraer parámetros del JSON
            Long rifaId = Long.valueOf(payload.get("rifaId").toString());
            Integer numeroBoleto = Integer.valueOf(payload.get("numeroBoleto").toString());
            
            // 🔒 EXTRAER EMAIL DESDE EL JWT DE FORMA SEGURA:
            String emailUsuario = authentication.getName();

            // Ejecutar la compra transaccional
            Boleto boletoComprado = boletoService.comprarBoleto(rifaId, numeroBoleto, emailUsuario);
            return ResponseEntity.ok(boletoComprado);
            
        } catch (Exception e) {
            // Retorna el mensaje de error personalizado de las validaciones del servicio
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
