package com.periziafacile.itemservice.application.usecase.ramo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

class UpdateRamoTest {
    private InMemoryRamoRepository repository;
    private UpdateRamo updateRamo;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
        updateRamo = new UpdateRamo(repository);
    }

    @Test
    void shouldUpdateExistingItem() {

        Ramo ramo = repository.save(new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli"));
        Ramo updated = new Ramo(ramo.getId(), "Veicoli (updated)", "Tipologie di servizi relativi a veicoli (updated)");
        Optional<Ramo> result = updateRamo.execute(ramo.getId(), updated);
        assertTrue(result.isPresent());
        assertEquals("Veicoli (updated)", result.get().getName());
        assertEquals("Tipologie di servizi relativi a veicoli (updated)", result.get().getDescription());
    }

    @Test
    void shouldReturnEmptyIfItemNotFound() {
        Ramo notExisting = new Ramo(null, "Veicoli", "Tipologie di servizi relativi a veicoli");
        Optional<Ramo> result = updateRamo.execute(notExisting.getId(), notExisting);
        assertTrue(result.isEmpty());
    }

}
