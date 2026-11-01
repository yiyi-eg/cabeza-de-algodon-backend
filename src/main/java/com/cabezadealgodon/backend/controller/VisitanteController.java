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

import com.cabezadealgodon.backend.model.Visitante;
import com.cabezadealgodon.backend.repository.VisitanteRepository;

@RestController
@RequestMapping("/api/visitantes")
@CrossOrigin(origins = "*")
public class VisitanteController {

    @Autowired
    private VisitanteRepository repository;

    @GetMapping
    public List<Visitante> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitante> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Visitante crear(@RequestBody Visitante visitante) {
        return repository.save(visitante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visitante> actualizar(@PathVariable Long id, @RequestBody Visitante details) {
        return repository.findById(id).map(visitante -> {
            visitante.setNombre(details.getNombre());
            visitante.setApellido(details.getApellido());
            visitante.setDpi(details.getDpi());
            visitante.setTelefono(details.getTelefono());
            return ResponseEntity.ok(repository.save(visitante));
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