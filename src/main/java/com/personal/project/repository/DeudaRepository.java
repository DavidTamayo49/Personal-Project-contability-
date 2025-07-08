package com.personal.project.repository;


import com.personal.project.domain.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeudaRepository extends JpaRepository<Deuda, UUID> {
}
