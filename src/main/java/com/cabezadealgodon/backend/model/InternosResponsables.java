package com.cabezadealgodon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "internos_responsables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternosResponsables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinternos_responsables")
    private Long idInternosResponsables;

    @Column(name = "internos_idinternos", nullable = false)
    private Long internoId;

    @Column(name = "responsable_idresponsable", nullable = false)
    private Long responsableId;

    @Column(nullable = false, length = 50)
    private String parentesco;

    @Column(name = "es_contacto_principal", nullable = false)
    private Boolean esContactoPrincipal = false;
}