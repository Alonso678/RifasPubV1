package com.rifas.v1.backend_rifas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "boletos", uniqueConstraints = {
    // ⚠️ CRUCIAL: Esto evita que se venda el mismo número dos veces en la misma rifa
    @UniqueConstraint(columnNames = {"rifa_id", "numero_boleto"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_boleto", nullable = false)
    private Integer numeroBoleto;

    @Column(nullable = false)
    private String estado; // "RESERVADO", "CONFIRMADO"

    @Column(name = "comprado_en")
    private LocalDateTime compradoEn;

    // Relación: Muchas boletos pertenecen a una Rifa
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rifa_id", nullable = false)
    private Rifa rifa;

    // Relación: Muchos boletos pertenecen a un Usuario (Comprador)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        this.compradoEn = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "CONFIRMADO"; // Por ahora entra directo como confirmado para el MVP
        }
    }
}
