package com.personal.project.service;


import com.personal.project.domain.MedioPago;
import com.personal.project.repository.MedioPagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedioPagoService {

    MedioPagoRepository medioPagoRepository;

    public MedioPagoService(MedioPagoRepository medioPagoRepository) {
        this.medioPagoRepository = medioPagoRepository;
    }

    private void validatePayMethod(MedioPago medioPago) {
        if (medioPago.getNombre() == null || medioPago.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del medio de pago es obligatorio");
        }

        if (medioPago.getNombre().length() > 35) {
            throw new IllegalArgumentException("El nombre del medio de pago no puede exceder los 25 caracteres");
        }
    }

    //Consultar medio de pago
    public List<MedioPago> findAllPayMetods() {
        return medioPagoRepository.findAll();
    }


    //Consuultar medio de pago por id
    public Optional<MedioPago> findById(UUID id) {
        return medioPagoRepository.findById(id);
    }

    //Crear medio de pago
    public void savePayMethod(MedioPago medioPago) {
        validatePayMethod(medioPago);
        medioPagoRepository.save(medioPago);
    }

    //Modificar medio de pago
    public void updatePayMethod(MedioPago newPayMethod) {
        Optional<MedioPago> payMethodOptional = medioPagoRepository.findById(newPayMethod.getId());

        if (payMethodOptional.isPresent()) {
            validatePayMethod(newPayMethod);

            MedioPago payMethod = payMethodOptional.get();
            payMethod.setNombre(newPayMethod.getNombre());
            medioPagoRepository.save(payMethod);
        } else {
            throw new IllegalArgumentException("Medio de pago no fue encontrado");
        }
    }

    //Eliminar medio de pago
    public void deletePayMethod(UUID id) {
        medioPagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El medio de pago que deseas eliminar no existe o ya ha sido eliminado"));
        medioPagoRepository.deleteById(id);
    }

}
