package com.cabezadealgodon.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FichaMedicaResponseDTO {
    private Long idFichaMedicaHistorial;
    private Long internoId;
    private Long usuarioId;
    private String diagnostico;
    private String tratamientoRecetado;
    private String observacionesEnfermeria;
    private LocalDateTime fechaRegistro;
    private LocalDateTime creadoEn;
}