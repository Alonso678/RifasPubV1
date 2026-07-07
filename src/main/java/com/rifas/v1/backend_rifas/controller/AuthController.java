package com.rifas.v1.backend_rifas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            // Spring Security se encarga de ir a buscar el usuario a Neon y verificar la contraseña con BCrypt
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "¡Autenticación exitosa, Alonso!",
                "user", authentication.getName()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "status", "error",
                "message", "Credenciales incorrectas: " + e.getMessage()
            ));
        }
    }
}
