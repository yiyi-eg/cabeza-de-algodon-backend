package com.cabezadealgodon.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabezadealgodon.backend.dto.VisitaResponseDTO;
import com.cabezadealgodon.backend.model.Visita;
import com.cabezadealgodon.backend.repository.VisitaRepository;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    public List<VisitaResponseDTO> listarTodasLasVisitas() {
        List<Visita> visitas = visitaRepository.findAll();
        return visitas.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public VisitaResponseDTO mapToDto(Visita visita) {
        return new VisitaResponseDTO(
            visita.getIdVisita(),
            visita.getFechaHoraEntrada(),
            visita.getFechaHoraSalida(),
            visita.getParentesco(),
            visita.getInternoId(), // 1. ID del interno
            visita.getVisitanteId(), // 2. ID del visitante
            visita.getRegistradoPor() != null ? visita.getRegistradoPor().getUsername() : null // 3. Username responsable
        );
    }
}