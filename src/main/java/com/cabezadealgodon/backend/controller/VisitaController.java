package com.cabezadealgodon.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.dto.VisitaResponseDTO;
import com.cabezadealgodon.backend.model.Usuario;
import com.cabezadealgodon.backend.model.Visita;
import com.cabezadealgodon.backend.model.Visitante;
import com.cabezadealgodon.backend.repository.UsuarioRepository;
import com.cabezadealgodon.backend.repository.VisitaRepository;
import com.cabezadealgodon.backend.repository.VisitanteRepository;
import com.cabezadealgodon.backend.service.NotificationService;

@RestController
@RequestMapping("/api/visitas")
@CrossOrigin(origins = "*")
public class VisitaController {

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private VisitanteRepository visitanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/visitantes")
    public List<Visitante> listarVisitantes() {
        return visitanteRepository.findAll();
    }

    @PostMapping("/visitantes")
    public Visitante crearVisitante(@RequestBody Visitante visitante) {
        return visitanteRepository.save(visitante);
    }

    @GetMapping
    public List<VisitaResponseDTO> listarVisitas() {
        List<Visita> visitas = visitaRepository.findAll();
        
        return visitas.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarEntrada(
            @RequestBody Visita visita,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        Usuario usuario = usuarioRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("No se encontró el usuario autenticado");
        }

        visita.setRegistradoPor(usuario);

        Visita nueva = visitaRepository.save(visita);

        // Notificamos enviando el DTO plano al WebSocket
        notificationService.notificarCambio("visitas", convertToDto(nueva));

        return ResponseEntity.ok(convertToDto(nueva));
    }

    @PutMapping("/{id}/salida")
    @Transactional
    public ResponseEntity<?> registrarSalida(@PathVariable Long id) {
        return visitaRepository.findById(id).map(visita -> {
            visita.setFechaHoraSalida(LocalDateTime.now());

            Visita actualizada = visitaRepository.save(visita);

            // Notificamos enviando el DTO plano al WebSocket
            notificationService.notificarCambio("visitas", convertToDto(actualizada));

            return ResponseEntity.ok(convertToDto(actualizada));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Método auxiliar privado para mapear la entidad a DTO
        private VisitaResponseDTO convertToDto(Visita visita) {
        return new VisitaResponseDTO(
            visita.getIdVisita(),
            visita.getFechaHoraEntrada(),
            visita.getFechaHoraSalida(),
            visita.getParentesco(),
            visita.getInternoId(),
            visita.getVisitanteId(),
            visita.getRegistradoPor() != null ? visita.getRegistradoPor().getUsername() : null
        );
    }
}