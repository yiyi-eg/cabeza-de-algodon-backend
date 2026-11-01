package com.cabezadealgodon.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabezadealgodon.backend.model.Responsable;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
}