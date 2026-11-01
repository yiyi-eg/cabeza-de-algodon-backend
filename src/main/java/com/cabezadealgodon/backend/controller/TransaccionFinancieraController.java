package com.cabezadealgodon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.model.TransaccionFinanciera;
import com.cabezadealgodon.backend.repository.TransaccionFinancieraRepository;
import com.cabezadealgodon.backend.service.NotificationService;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
public class TransaccionFinancieraController {

    @Autowired
    private TransaccionFinancieraRepository repository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<TransaccionFinanciera> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/interno/{internoId}")
    public List<TransaccionFinanciera> listarPorInterno(@PathVariable Long internoId) {
        return repository.findByInternoIdInterno(internoId);
    }

    @PostMapping
    public TransaccionFinanciera crear(@RequestBody TransaccionFinanciera transaccion) {
        TransaccionFinanciera nueva = repository.save(transaccion);
        notificationService.notificarCambio("finanzas", nueva);
        return nueva;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionFinanciera> actualizar(@PathVariable Long id, @RequestBody TransaccionFinanciera details) {
        return repository.findById(id).map(transaccion -> {
            transaccion.setTipo(details.getTipo());
            transaccion.setMonto(details.getMonto());
            transaccion.setDescripcion(details.getDescripcion());
            transaccion.setFechaTransaccion(details.getFechaTransaccion());
            transaccion.setEstadoPago(details.getEstadoPago());
            TransaccionFinanciera actualizada = repository.save(transaccion);
            notificationService.notificarCambio("finanzas", actualizada);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }
}