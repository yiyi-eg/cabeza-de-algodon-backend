package com.cabezadealgodon.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cabezadealgodon.backend.model.FichaMedicaHistorial;

public interface FichaMedicaHistorialRepository extends JpaRepository<FichaMedicaHistorial, Long> {

    // Trae interno y usuario en una sola consulta, más recientes primero.
    @Query("SELECT f FROM FichaMedicaHistorial f " +
           "LEFT JOIN FETCH f.interno " +
           "LEFT JOIN FETCH f.usuario " +
           "ORDER BY f.fechaRegistro DESC")
    List<FichaMedicaHistorial> findAllConRelaciones();

    @Query("SELECT f FROM FichaMedicaHistorial f " +
           "JOIN FETCH f.interno i " +
           "LEFT JOIN FETCH f.usuario " +
           "WHERE i.idInterno = :internoId " +
           "ORDER BY f.fechaRegistro DESC")
    List<FichaMedicaHistorial> findByInternoIdConRelaciones(@Param("internoId") Long internoId);
}