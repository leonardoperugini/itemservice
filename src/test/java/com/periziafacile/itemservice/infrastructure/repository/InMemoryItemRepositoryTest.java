package com.periziafacile.itemservice.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;

class InMemoryItemRepositoryTest {

    private InMemoryItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
    }

    @Test
    void shouldSaveAndFindItemById() {
        Item item = new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00"));
        Item saved = repository.save(item);

        Optional<Item> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Perizia Medica", found.get().getName());
        assertEquals("Perizia Medica su documentazione", found.get().getDescription());
        assertEquals(new BigDecimal("89.00"), found.get().getPrice());
    }

    @Test
    void shouldReturnEmptyWhenItemNotFound() {
        Optional<Item> found = repository.findById(999L);
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));
        repository.save(new Item(null, "Perizia Veicoli", "Perizia Veicoli su documentazione", new BigDecimal("59.00")));

        List<Item> items = repository.findAll();
        assertEquals(2, items.size());
    }

    @Test
    void shouldDeleteById() {
        Item item = repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));
        repository.deleteById(item.getId());

        Optional<Item> found = repository.findById(item.getId());
        assertTrue(found.isEmpty());
    }
}