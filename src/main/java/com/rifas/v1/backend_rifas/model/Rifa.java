package com.rifas.v1.backend_rifas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rifas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio_boleto", nullable = false)
    private Double precioBoleto;

    @Column(name = "total_boletos", nullable = false)
    private Integer totalBoletos;

    @Column(name = "boletos_disponibles", nullable = false)
    private Integer boletosDisponibles;

    @Column(name = "fecha_sorteo")
    private LocalDateTime fechaSorteo;

    @Column(nullable = false)
    private String estado; // Ejemplo: "ACTIVA", "FINALIZADA", "PAUSADA"

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    // Se ejecuta automáticamente antes de insertar en la BD
    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
        if (this.boletosDisponibles == null) {
            this.boletosDisponibles = this.totalBoletos;
        }
        if (this.estado == null) {
            this.estado = "ACTIVA";
        }
    }
}
