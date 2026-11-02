package com.cabezadealgodon.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.dto.TransaccionFinancieraResponse;
import com.cabezadealgodon.backend.model.TransaccionFinanciera;
import com.cabezadealgodon.backend.repository.TransaccionFinancieraRepository;
import com.cabezadealgodon.backend.service.NotificationService;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
@Transactional
public class TransaccionFinancieraController {

    @Autowired
    private TransaccionFinancieraRepository repository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<TransaccionFinancieraResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @GetMapping("/interno/{internoId}")
    public List<TransaccionFinancieraResponse> listarPorInterno(
            @PathVariable Long internoId) {

        return repository.findByInternoIdInterno(internoId)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @PostMapping
    public TransaccionFinancieraResponse crear(
            @RequestBody TransaccionFinanciera transaccion) {

        TransaccionFinanciera nueva = repository.save(transaccion);

        TransaccionFinancieraResponse respuesta = convertirDTO(nueva);

        notificationService.notificarCambio("finanzas", respuesta);

        return respuesta;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionFinancieraResponse> actualizar(
            @PathVariable Long id,
            @RequestBody TransaccionFinanciera details) {

        return repository.findById(id).map(transaccion -> {

            transaccion.setTipo(details.getTipo());
            transaccion.setMonto(details.getMonto());
            transaccion.setDescripcion(details.getDescripcion());
            transaccion.setFechaTransaccion(details.getFechaTransaccion());
            transaccion.setEstadoPago(details.getEstadoPago());

            TransaccionFinanciera actualizada =
                    repository.save(transaccion);

            TransaccionFinancieraResponse respuesta =
                    convertirDTO(actualizada);

            notificationService.notificarCambio("finanzas", respuesta);

            return ResponseEntity.ok(respuesta);

        }).orElse(ResponseEntity.notFound().build());
    }

    private TransaccionFinancieraResponse convertirDTO(
            TransaccionFinanciera transaccion) {

        return new TransaccionFinancieraResponse(
                transaccion.getIdTransaccionFinanciera(),
                transaccion.getMonto(),
                transaccion.getDescripcion(),
                transaccion.getFechaTransaccion(),
                transaccion.getTipo(),
                transaccion.getEstadoPago(),

                transaccion.getInterno().getIdInterno(),
                transaccion.getInterno().getNombre(),
                transaccion.getInterno().getApellido(),

                transaccion.getRegistradoPor().getUsername(),

                transaccion.getCreadoEn(),
                transaccion.getActualizadoEn()
        );
    }
}