package com.personal.project.repository;

import com.personal.project.domain.Abono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AbonoRepository extends JpaRepository<Abono, UUID> {
}
