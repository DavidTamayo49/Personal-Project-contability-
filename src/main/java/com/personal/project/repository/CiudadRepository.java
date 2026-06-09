package com.personal.project.repository;


import com.personal.project.domain.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CiudadRepository extends JpaRepository<Ciudad, UUID> {
}
