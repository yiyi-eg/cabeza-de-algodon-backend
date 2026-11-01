package com.cabezadealgodon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_referencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsolicitud_referencia")
    private Long idSolicitudReferencia;

    @Column(name = "nombre_aspirante", nullable = false, length = 150)
    private String nombreAspirante;

    @Column(name = "dpi_aspirante", nullable = false, unique = true, length = 13)
    private String dpiAspirante;

    @Column(name = "edad_aspirante", nullable = false)
    private Integer edadAspirante;

    @Column(name = "nombre_solicitante", nullable = false, length = 150)
    private String nombreSolicitante;

    @Column(nullable = false, length = 50)
    private String parentesco;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(name = "motivo_ingreso", nullable = false, columnDefinition = "TEXT")
    private String motivoIngreso;

    @Column(name = "estado_solicitud", nullable = false, length = 30)
    private String estadoSolicitud = "PENDIENTE";

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}