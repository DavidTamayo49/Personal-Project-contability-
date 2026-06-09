package com.personal.project.service;

import com.personal.project.domain.TipoMovimiento;
import com.personal.project.repository.TipoMovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoMovimientoService {
    private TipoMovimientoRepository tipoMovimientoRepository;

    public TipoMovimientoService(TipoMovimientoRepository tipoMovimientoRepository) {
        this.tipoMovimientoRepository = tipoMovimientoRepository;
    }

    public List<TipoMovimiento> findTiposMovimiento() {
        return tipoMovimientoRepository.findAll();
    }

    @Transactional
    public TipoMovimiento findTipoMovimientoById(UUID id) {
        return tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de movimiento no encontrado"));
    }

}
