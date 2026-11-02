package com.cabezadealgodon.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabezadealgodon.backend.model.TransaccionFinanciera;

@Repository
public interface TransaccionFinancieraRepository
        extends JpaRepository<TransaccionFinanciera, Long> {

    @Override
    @EntityGraph(attributePaths = {"interno", "registradoPor"})
    List<TransaccionFinanciera> findAll();

    @EntityGraph(attributePaths = {"interno", "registradoPor"})
    List<TransaccionFinanciera> findByInternoIdInterno(Long internoId);
}