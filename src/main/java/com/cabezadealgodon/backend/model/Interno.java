package com.cabezadealgodon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "interno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinterno")
    private Long idInterno;

    @Column(name = "solicitud_referencia_idsolicitud_referencia", nullable = false)
    private Long solicitudReferenciaId;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 50)
    private String dpi;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 20)
    private String genero;

    @Column(name = "numero_habitacion", nullable = false, length = 45)
    private String numeroHabitacion;

    @Column(name = "numero_cama", nullable = false, length = 45)
    private String numeroCama;

    @Column(name = "tipo_sangre", nullable = false, length = 20)
    private String tipoSangre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "condiciones_cronicas", nullable = false, columnDefinition = "TEXT")
    private String condicionesCronicas;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false, length = 45)
    private String estado = "ACTIVO";

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