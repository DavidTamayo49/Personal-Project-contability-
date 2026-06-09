package com.personal.project.repository;


import com.personal.project.domain.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedioPagoRepository extends JpaRepository<MedioPago, UUID> {
}
