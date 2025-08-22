package com.periziafacile.itemservice.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

class ListRamiTest {
    private InMemoryRamoRepository repository;
    private ListRami listRami;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
        listRami = new ListRami(repository);
    }

    @Test
    void shouldReturnAllRami() {
        repository.save(new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli"));
        repository.save(new Ramo(null, "Immobili", "Tipologie di servizi relativi a immobili"));
        assertEquals(2, listRami.execute().size());
    }

    @Test
    void shouldReturnEmptyListIfNoRami() {
        assertTrue(listRami.execute().isEmpty());
    }
}
