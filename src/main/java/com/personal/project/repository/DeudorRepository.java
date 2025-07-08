package com.personal.project.repository;


import com.personal.project.domain.Deudor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeudorRepository extends JpaRepository<Deudor, UUID> {
}
