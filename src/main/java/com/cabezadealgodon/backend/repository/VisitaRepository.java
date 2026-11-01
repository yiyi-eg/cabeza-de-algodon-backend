package com.cabezadealgodon.backend.repository;

import com.cabezadealgodon.backend.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
    List<Visita> findByInternoId(Long internoId);
}