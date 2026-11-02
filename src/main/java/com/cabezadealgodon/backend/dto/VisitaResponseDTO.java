package com.cabezadealgodon.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitaResponseDTO {
    private Long idVisita;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private String parentesco;
    private Long internoId;      // Cambiado de idInterno a internoId
    private Long visitanteId;    // Cambiado de idVisitante a visitanteId
    private String registradoPor; // Cambiado para que coincida si Flutter busca este nombre
}