package com.cabezadealgodon.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabezadealgodon.backend.model.FichaMedicaHistorial;

public interface FichaMedicaHistorialRepository extends JpaRepository<FichaMedicaHistorial, Long> {

    List<FichaMedicaHistorial> findByInternoIdInterno(Long internoId);
}