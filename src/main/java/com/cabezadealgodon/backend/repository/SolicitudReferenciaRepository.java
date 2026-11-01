package com.cabezadealgodon.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cabezadealgodon.backend.model.SolicitudReferencia;

@Repository
public interface SolicitudReferenciaRepository extends JpaRepository<SolicitudReferencia, Long> {
}