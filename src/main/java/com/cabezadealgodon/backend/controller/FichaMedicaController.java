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

import com.cabezadealgodon.backend.model.FichaMedicaHistorial;
import com.cabezadealgodon.backend.repository.FichaMedicaHistorialRepository;
import com.cabezadealgodon.backend.service.NotificationService;

@RestController
@RequestMapping("/api/fichas-medicas")
@CrossOrigin(origins = "*")
public class FichaMedicaController {

    @Autowired
    private FichaMedicaHistorialRepository repository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<FichaMedicaHistorial> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/interno/{internoId}")
    public List<FichaMedicaHistorial> listarPorInterno(@PathVariable Long internoId) {
        return repository.findByInternoIdInterno(internoId);
    }

    @PostMapping
    public FichaMedicaHistorial crear(@RequestBody FichaMedicaHistorial ficha) {
        FichaMedicaHistorial nueva = repository.save(ficha);
        notificationService.notificarCambio("alertas-medicas", nueva);
        return nueva;
    }
}