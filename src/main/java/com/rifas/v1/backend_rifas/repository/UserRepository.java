package com.rifas.v1.backend_rifas.repository;

import com.rifas.v1.backend_rifas.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Usuario, Long> {
    // Método personalizado para verificar si un correo ya está registrado
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
