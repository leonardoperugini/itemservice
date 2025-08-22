package com.periziafacile.itemservice.application.usecase.ramo;

import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;

public class GetRamo{
    private final RamoRepository ramoRepository;

    public GetRamo(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public Optional<Ramo> execute(Long id) {
        return ramoRepository.findById(id);
    }
}