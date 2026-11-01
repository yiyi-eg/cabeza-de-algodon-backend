package com.cabezadealgodon.backend.controller;

import com.cabezadealgodon.backend.model.SolicitudReferencia;
import com.cabezadealgodon.backend.repository.SolicitudReferenciaRepository;
import com.cabezadealgodon.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudReferenciaController {

    @Autowired
    private SolicitudReferenciaRepository repository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<SolicitudReferencia> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudReferencia> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SolicitudReferencia crear(@RequestBody SolicitudReferencia solicitud) {
        SolicitudReferencia nueva = repository.save(solicitud);
        notificationService.notificarCambio("solicitudes", nueva);
        return nueva;
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudReferencia> actualizar(@PathVariable Long id, @RequestBody SolicitudReferencia details) {
        return repository.findById(id).map(solicitud -> {
            solicitud.setNombreAspirante(details.getNombreAspirante());
            solicitud.setDpiAspirante(details.getDpiAspirante());
            solicitud.setEdadAspirante(details.getEdadAspirante());
            solicitud.setNombreSolicitante(details.getNombreSolicitante());
            solicitud.setParentesco(details.getParentesco());
            solicitud.setTelefono(details.getTelefono());
            solicitud.setMotivoIngreso(details.getMotivoIngreso());
            solicitud.setEstadoSolicitud(details.getEstadoSolicitud());
            solicitud.setFechaSolicitud(details.getFechaSolicitud());
            SolicitudReferencia actualizada = repository.save(solicitud);
            notificationService.notificarCambio("solicitudes", actualizada);
            return ResponseEntity.ok(actualizada);
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