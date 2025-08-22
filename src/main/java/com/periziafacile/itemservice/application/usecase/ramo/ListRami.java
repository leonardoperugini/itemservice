package com.periziafacile.itemservice.application.usecase.ramo;

import java.util.List;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;

public class ListRami {
    private final RamoRepository ramoRepository;

    public ListRami(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public List<Ramo> execute() {
        return ramoRepository.findAll();
    }
}