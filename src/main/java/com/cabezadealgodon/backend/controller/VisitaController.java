package com.cabezadealgodon.backend.controller;

import com.cabezadealgodon.backend.model.Visita;
import com.cabezadealgodon.backend.model.Visitante;
import com.cabezadealgodon.backend.repository.VisitaRepository;
import com.cabezadealgodon.backend.repository.VisitanteRepository;
import com.cabezadealgodon.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visitas")
@CrossOrigin(origins = "*")
public class VisitaController {

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private VisitanteRepository visitanteRepository;

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
    public List<Visita> listarVisitas() {
        return visitaRepository.findAll();
    }

    @PostMapping
    public Visita registrarEntrada(@RequestBody Visita visita) {
        Visita nueva = visitaRepository.save(visita);
        notificationService.notificarCambio("visitas", nueva);
        return nueva;
    }

    @PutMapping("/{id}/salida")
    public ResponseEntity<Visita> registrarSalida(@PathVariable Long id) {
        return visitaRepository.findById(id).map(visita -> {
            visita.setFechaHoraSalida(LocalDateTime.now());
            Visita actualizada = visitaRepository.save(visita);
            notificationService.notificarCambio("visitas", actualizada);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }
}