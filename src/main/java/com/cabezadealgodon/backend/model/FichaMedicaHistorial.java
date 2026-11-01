package com.cabezadealgodon.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fichamedicahistorial")
public class FichaMedicaHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfichamedicahistorial")
    private Long idFichaMedicaHistorial;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "tratamiento_recetado", nullable = false, columnDefinition = "TEXT")
    private String tratamientoRecetado;

    @Column(name = "observaciones_enfermeria", nullable = false, columnDefinition = "TEXT")
    private String observacionesEnfermeria;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interno_idinterno", nullable = false)
    private Interno interno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_idusuario", nullable = false)
    private Usuario medicoResponsable;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
}
