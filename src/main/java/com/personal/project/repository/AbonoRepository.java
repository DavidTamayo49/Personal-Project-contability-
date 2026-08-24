package com.personal.project.repository;

import com.personal.project.domain.Abono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AbonoRepository extends JpaRepository<Abono, UUID> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Abono a SET a.deudor = null WHERE a.deudor.id = :deudorId")
    void removeDeudorReferences(@Param("deudorId") UUID deudorId);
}
