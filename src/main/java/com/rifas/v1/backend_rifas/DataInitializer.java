package com.rifas.v1.backend_rifas;

import com.rifas.v1.backend_rifas.model.Usuario;
import com.rifas.v1.backend_rifas.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            String emailAdmin = "alonso@correo.com";

            // 1. Validamos si el usuario ya existe para no duplicarlo en cada reinicio
            if (!userRepository.existsByEmail(emailAdmin)) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                
                Usuario admin = new Usuario();
                admin.setNombre("Alonso Ortiz");
                admin.setEmail(emailAdmin);
                admin.setPassword(encoder.encode("admin123")); // Encripta dinámicamente
                admin.setActivo(true);
                
                // 2. Guardamos físicamente en Neon
                userRepository.save(admin);
                
                System.out.println("==================================================");
                System.out.println("✅ ¡Usuario administrador creado con éxito en Neon!");
                System.out.println("==================================================");
            } else {
                System.out.println("ℹ️ El usuario administrador ya existe en la base de datos.");
            }
        };
    }
}
