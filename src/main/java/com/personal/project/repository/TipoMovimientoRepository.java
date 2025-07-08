package com.personal.project.repository;


import com.personal.project.domain.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, UUID> {
}
