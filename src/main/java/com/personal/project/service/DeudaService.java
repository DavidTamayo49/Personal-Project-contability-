package com.personal.project.service;


import com.personal.project.domain.Deuda;
import com.personal.project.domain.Proveedor;
import com.personal.project.repository.DeudaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeudaService {
    private final DeudaRepository deudaRepository;
    private final ProveedorService proveedorService;
    private final MovimientoService movimientoService;

    public DeudaService(DeudaRepository deudaRepository, ProveedorService proveedorService,
                        MovimientoService movimientoService) {
        this.deudaRepository = deudaRepository;
        this.proveedorService = proveedorService;
        this.movimientoService = movimientoService;
    }

    private void validateDebt(Deuda deuda) {
        if (deuda == null) {
            throw new IllegalArgumentException("Los datos de la deuda son obligatorios");
        }

        if (deuda.getProveedor() == null || deuda.getProveedor().getId() == null) {
            throw new IllegalArgumentException("El proveedor es obligatorio");
        }

        if (deuda.getValor() <= 0) {
            throw new IllegalArgumentException("El valor de la deuda debe ser mayor a 0");
        }
    }

    private Proveedor findProvider(UUID providerId) {
        return proveedorService.findProviderById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no fue encontrado"));
    }
    //Consultar las deudas
    public List<Deuda> findAllDebts() {
        return deudaRepository.findAll();
    }

    public Optional<Deuda> findDebtById(UUID id) {
        return deudaRepository.findById(id);
    }

    //Eliminar deuda
    public void deleteDebt(UUID id) {
        deudaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "La deuda que deseas eliminar no existe o ya ha sido eliminada"));
        deudaRepository.deleteById(id);
    }

    //Registrar deuda
    public void saveDebt(Deuda deuda) {
        validateDebt(deuda);
        deuda.setProveedor(findProvider(deuda.getProveedor().getId()));
        deudaRepository.save(deuda);
    }


    //Modificar la deuda
    public void updateDebt(Deuda newDebt) {
        if (newDebt == null || newDebt.getId() == null) {
            throw new IllegalArgumentException("El id de la deuda es obligatorio");
        }

        Optional<Deuda> debtOptional = deudaRepository.findById(newDebt.getId());

        if (debtOptional.isPresent()) {
            validateDebt(newDebt);

            Deuda debt = debtOptional.get();
            debt.setValor(newDebt.getValor());
            debt.setProveedor(findProvider(newDebt.getProveedor().getId()));

            deudaRepository.save(debt);
        } else {
            throw new IllegalArgumentException("Deuda no fue encontrada");
        }
    }

    //Pagar deuda
    @Transactional
    public void payDebt(UUID debtId, UUID payMethodId) {
        if (payMethodId == null) {
            throw new IllegalArgumentException("El medio de pago es obligatorio");
        }

        Deuda deuda = deudaRepository.findById(debtId)
                .orElseThrow(() -> new IllegalArgumentException("Deuda no fue encontrada"));
        Proveedor proveedor = findProvider(deuda.getProveedor().getId());

        movimientoService.saveProviderDebtPayment(
                proveedor.getNombre(), deuda.getValor(), payMethodId);

        deleteDebt(debtId);
    }

}
