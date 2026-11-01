package com.cabezadealgodon.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabezadealgodon.backend.model.TransaccionFinanciera;

public interface TransaccionFinancieraRepository extends JpaRepository<TransaccionFinanciera, Long> {

    List<TransaccionFinanciera> findByInternoIdInterno(Long internoId);
}