package com.cabezadealgodon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.model.InternosResponsables;
import com.cabezadealgodon.backend.repository.InternosResponsablesRepository;

@RestController
@RequestMapping("/api/internos-responsables")
@CrossOrigin(origins = "*")
public class InternosResponsablesController {

    @Autowired
    private InternosResponsablesRepository repository;

    @GetMapping
    public List<InternosResponsables> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/interno/{internoId}")
    public List<InternosResponsables> listarPorInterno(@PathVariable Long internoId) {
        return repository.findByInternoId(internoId);
    }

    @PostMapping
    public InternosResponsables asignarResponsable(@RequestBody InternosResponsables relacion) {
        return repository.save(relacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}