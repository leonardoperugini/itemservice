package com.periziafacile.itemservice.application.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

class DeleteRamoTest {
    private InMemoryRamoRepository repository;
    private DeleteRamo deleteRamo;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
        deleteRamo = new DeleteRamo(repository);
    }

    @Test
    void shouldDeleteExistingItem() {
        Ramo ramo = new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli");
        deleteRamo.execute(ramo.getId());
        assertTrue(repository.findById(ramo.getId()).isEmpty());
    }

    @Test
    void shouldNotThrowIfItemNotFound() {
        assertDoesNotThrow(() -> deleteRamo.execute(999L));
    }
}
