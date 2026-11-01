package com.cabezadealgodon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.model.Interno;
import com.cabezadealgodon.backend.repository.InternoRepository;
import com.cabezadealgodon.backend.service.NotificationService;

@RestController
@RequestMapping("/api/internos")
@CrossOrigin(origins = "*")
public class InternoController {

    @Autowired
    private InternoRepository repository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Interno> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interno> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Interno crear(@RequestBody Interno interno) {
        Interno nuevo = repository.save(interno);
        notificationService.notificarCambio("internos", nuevo);
        return nuevo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interno> actualizar(@PathVariable Long id, @RequestBody Interno details) {
        return repository.findById(id).map(interno -> {
            interno.setNombre(details.getNombre());
            interno.setApellido(details.getApellido());
            interno.setDpi(details.getDpi());
            interno.setFechaNacimiento(details.getFechaNacimiento());
            interno.setGenero(details.getGenero());
            interno.setNumeroHabitacion(details.getNumeroHabitacion());
            interno.setNumeroCama(details.getNumeroCama());
            interno.setTipoSangre(details.getTipoSangre());
            interno.setAlergias(details.getAlergias());
            interno.setCondicionesCronicas(details.getCondicionesCronicas());
            interno.setFechaIngreso(details.getFechaIngreso());
            interno.setEstado(details.getEstado());
            Interno actualizado = repository.save(interno);
            notificationService.notificarCambio("internos", actualizado);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}