package com.periziafacile.itemservice.application.usecase.ramo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

class CreateRamoTest {
    private InMemoryRamoRepository repository;
    private CreateRamo createRamo;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
        createRamo = new CreateRamo(repository);
    }

    @Test
    void shouldCreateNewRamo() {
        Ramo ramo = new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli");
        Ramo saved = createRamo.execute(ramo);
        assertNotNull(saved.getId());
        assertEquals("Veicoli", saved.getName());
        assertTrue(repository.findById(saved.getId()).isPresent());
    }
}
