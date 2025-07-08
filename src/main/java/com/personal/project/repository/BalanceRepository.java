package com.personal.project.repository;


import com.personal.project.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
}
