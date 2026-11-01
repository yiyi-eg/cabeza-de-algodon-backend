package com.cabezadealgodon.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cabezadealgodon.backend.model.Interno;

import java.util.Optional;

@Repository
public interface InternoRepository extends JpaRepository<Interno, Long> {
    Optional<Interno> findByDpi(String dpi);
}