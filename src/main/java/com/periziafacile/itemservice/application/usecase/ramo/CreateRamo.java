package com.periziafacile.itemservice.application.usecase.ramo;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;

public class CreateRamo {
    private final RamoRepository ramoRepository;

    public CreateRamo(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public Ramo execute(Ramo ramo) {
        return ramoRepository.save(ramo);
    }
}