package com.cabezadealgodon.backend.repository;

import com.cabezadealgodon.backend.model.InternosResponsables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InternosResponsablesRepository extends JpaRepository<InternosResponsables, Long> {
    List<InternosResponsables> findByInternoId(Long internoId);
}