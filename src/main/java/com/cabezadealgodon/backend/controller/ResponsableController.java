package com.cabezadealgodon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.model.InternosResponsables;
import com.cabezadealgodon.backend.model.Responsable;
import com.cabezadealgodon.backend.repository.InternosResponsablesRepository;
import com.cabezadealgodon.backend.repository.ResponsableRepository;

@RestController
@RequestMapping("/api/responsables")
@CrossOrigin(origins = "*")
public class ResponsableController {

    @Autowired
    private ResponsableRepository responsableRepository;

    @Autowired
    private InternosResponsablesRepository internosResponsablesRepository;

    @GetMapping
    public List<Responsable> listarTodos() {
        return responsableRepository.findAll();
    }

    @PostMapping
    public Responsable crear(@RequestBody Responsable responsable) {
        return responsableRepository.save(responsable);
    }

    @PostMapping("/asignar-a-interno")
    public InternosResponsables asignarInterno(@RequestBody InternosResponsables relacion) {
        return internosResponsablesRepository.save(relacion);
    }

    @GetMapping("/interno/{internoId}")
    public List<InternosResponsables> listarPorInterno(@PathVariable Long internoId) {
        return internosResponsablesRepository.findByInternoId(internoId);
    }
}