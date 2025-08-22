package com.periziafacile.itemservice.domain.port;

import java.util.List;
import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Ramo;

public interface RamoRepository {
    Optional<Ramo> findById(Long id);
    List<Ramo> findAll();
    Ramo save(Ramo item);
    void deleteById(Long id);
}