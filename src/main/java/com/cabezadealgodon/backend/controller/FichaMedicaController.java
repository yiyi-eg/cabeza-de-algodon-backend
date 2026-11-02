package com.cabezadealgodon.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabezadealgodon.backend.dto.FichaMedicaResponseDTO;
import com.cabezadealgodon.backend.model.FichaMedicaHistorial;
import com.cabezadealgodon.backend.model.Usuario;
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
    @Transactional(readOnly = true)
    public List<FichaMedicaResponseDTO> listarTodas() {
        return repository.findAllConRelaciones().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/interno/{internoId}")
    @Transactional(readOnly = true)
    public List<FichaMedicaResponseDTO> listarPorInterno(@PathVariable Long internoId) {
        return repository.findByInternoIdConRelaciones(internoId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<FichaMedicaResponseDTO> crear(@RequestBody FichaMedicaHistorial ficha,
                                                        @AuthenticationPrincipal Usuario actual) {
        // El interno es obligatorio: sin esto convertToDto fallaba con NullPointerException (500)
        if (ficha.getInterno() == null || ficha.getInterno().getIdInterno() == null) {
            throw new IllegalArgumentException("La ficha debe incluir un interno válido.");
        }

        // Si la ficha llega sin usuario, se asigna el usuario autenticado
        // (evita "usuario_idusuario" NULL).
        if (ficha.getUsuario() == null || ficha.getUsuario().getIdUsuario() == null) {
            if (actual == null) {
                throw new IllegalArgumentException("No se pudo identificar al usuario autenticado.");
            }
            ficha.setUsuario(actual);
        }

        FichaMedicaHistorial nueva = repository.save(ficha);

        FichaMedicaResponseDTO responseDTO = convertToDto(nueva);
        notificationService.notificarCambio("alertas-medicas", responseDTO);

        return ResponseEntity.ok(responseDTO);
    }

    // Null-safe: una ficha vieja sin interno o usuario ya no tumba todo el listado.
    private FichaMedicaResponseDTO convertToDto(FichaMedicaHistorial ficha) {
        Long internoId = ficha.getInterno() != null ? ficha.getInterno().getIdInterno() : null;
        Long usuarioId = ficha.getUsuario() != null ? ficha.getUsuario().getIdUsuario() : null;

        return new FichaMedicaResponseDTO(
                ficha.getIdFichaMedicaHistorial(),
                internoId,
                usuarioId,
                ficha.getDiagnostico(),
                ficha.getTratamientoRecetado(),
                ficha.getObservacionesEnfermeria(),
                ficha.getFechaRegistro(),
                ficha.getCreadoEn()
        );
    }
}