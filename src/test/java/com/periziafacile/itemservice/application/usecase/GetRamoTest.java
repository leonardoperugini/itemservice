package com.periziafacile.itemservice.application.usecase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

class GetRamoTest {
    private InMemoryRamoRepository repository;
    private GetRamo getRamo;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
        getRamo = new GetRamo(repository);
    }

    @Test
    void shouldReturnRamoById() {
        Ramo ramo = repository.save(new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli"));
        Optional<Ramo> found = getRamo.execute(ramo.getId());
        assertTrue(found.isPresent());
        assertEquals(ramo.getId(), found.get().getId());
    }

    @Test
    void shouldReturnEmptyIfNotFound() {
        Optional<Ramo> result = getRamo.execute(999L);
        assertTrue(result.isEmpty());
    }
}
