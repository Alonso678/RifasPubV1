package com.rifas.v1.backend_rifas.controller;

import com.rifas.v1.backend_rifas.utils.security.JwtUtils;
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
    private final JwtUtils jwtUtils; // 1. Inyectamos la utilidad de JWT

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            // Spring Security valida contra Neon que las credenciales sean correctas
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 2. Si la autenticación fue exitosa, generamos su Token JWT real
            String token = jwtUtils.generateToken(authentication.getName());

            // 3. Devolvemos el token estructurado al cliente
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "¡Autenticación exitosa!",
                "token", token, // El frontend guardará este string largo
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
