package com.periziafacile.itemservice.application.usecase;

import com.periziafacile.itemservice.domain.port.RamoRepository;

public class DeleteRamo {
    private final RamoRepository ramoRepository;

    public DeleteRamo(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public void execute(Long id) {
        ramoRepository.deleteById(id);
    }
}