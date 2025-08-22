package com.periziafacile.itemservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Ramo;

class InMemoryRamoRepositoryTest {

    private InMemoryRamoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRamoRepository();
    }

    @Test
    void shouldSaveAndFindRamoById() {
        Ramo ramo = new Ramo(null, "Ramo Vita", "Assicurazione sulla vita");
        Ramo saved = repository.save(ramo);

        Optional<Ramo> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Ramo Vita", found.get().getName());
        assertEquals("Assicurazione sulla vita", found.get().getDescription());
    }

    @Test
    void shouldReturnEmptyWhenRamoNotFound() {
        Optional<Ramo> found = repository.findById(999L);
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindAllRami() {
        repository.save(new Ramo(null, "Ramo Vita", "Assicurazione sulla vita"));
        repository.save(new Ramo(null, "Ramo Auto", "Assicurazione auto"));

        List<Ramo> rami = repository.findAll();
        assertEquals(2, rami.size());
    }

    @Test
    void shouldDeleteById() {
        Ramo ramo = repository.save(new Ramo(null, "Ramo Vita", "Assicurazione sulla vita"));
        repository.deleteById(ramo.getId());

        Optional<Ramo> found = repository.findById(ramo.getId());
        assertTrue(found.isEmpty());
    }
}