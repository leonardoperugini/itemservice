package com.periziafacile.itemservice.application.usecase;

import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;

public class UpdateRamo {
    private final RamoRepository ramoRepository;

    public UpdateRamo(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public Optional<Ramo> execute(Long id, Ramo updatedRamo) {
        return ramoRepository.findById(id).map(existing -> {
            existing.setName(updatedRamo.getName());
            existing.setDescription(updatedRamo.getDescription());
            return ramoRepository.save(existing);
        });
    }
}