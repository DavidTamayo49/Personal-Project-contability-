package com.personal.project.repository;


import com.personal.project.domain.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, UUID> {
}
