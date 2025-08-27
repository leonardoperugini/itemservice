package com.periziafacile.itemservice.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.model.Ramo;

class InMemoryItemRepositoryTest {

    private InMemoryItemRepository item_repository;
    private InMemoryRamoRepository ramo_repository;

    @BeforeEach
    void setUp() {
        item_repository = new InMemoryItemRepository();
        ramo_repository = new InMemoryRamoRepository();
        ramo_repository.save(new Ramo(null, "Ramo Test", "Descrizione Ramo Test"));
    }

    @Test
    void shouldSaveAndFindItemById() {
        Ramo ramo = ramo_repository.findById(1L).get();
        
        Item item = new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00"), ramo);
        Item saved = item_repository.save(item);

        Optional<Item> found = item_repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Perizia Medica", found.get().getName());
        assertEquals("Perizia Medica su documentazione", found.get().getDescription());
        assertEquals(new BigDecimal("89.00"), found.get().getPrice());
    }

    @Test
    void shouldReturnEmptyWhenItemNotFound() {
        Optional<Item> found = item_repository.findById(999L);
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        Ramo ramo = new Ramo(10L, "Ramo Test", "Descrizione Ramo Test");
        Item item = new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00"), ramo);
        item_repository.save(item);
                
        item = new Item(null, "Perizia Veicoli", "Perizia Veicoli su documentazione", new BigDecimal("59.00"));
        item_repository.save(item);

        //item_repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));
        //item_repository.save(item);

        List<Item> items = item_repository.findAll();
        assertEquals(2, items.size());
    }

    @Test
    void shouldDeleteById() {
        Item item = item_repository
                .save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));
        item_repository.deleteById(item.getId());

        Optional<Item> found = item_repository.findById(item.getId());
        assertTrue(found.isEmpty());
    }
}