package com.cabezadealgodon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "visita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvisita")
    private Long idVisita;

    @Column(name = "interno_idinterno", nullable = false)
    private Long internoId;

    @Column(name = "visitante_idvisitante", nullable = false)
    private Long visitanteId;

    @Column(nullable = false, length = 50)
    private String parentesco;

    @Column(name = "fecha_hora_entrada", nullable = false)
    private LocalDateTime fechaHoraEntrada;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    protected void onCreate() {
        if (fechaHoraEntrada == null) {
            fechaHoraEntrada = LocalDateTime.now();
        }
        creadoEn = LocalDateTime.now();
    }
}