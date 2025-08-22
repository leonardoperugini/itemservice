package com.periziafacile.itemservice.application.usecase.item;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;

class UpdateItemTest {
    private InMemoryItemRepository repository;
    private UpdateItem updateItem;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        updateItem = new UpdateItem(repository);
    }

    @Test
    void shouldUpdateExistingItem() {

        Item item = repository.save(new Item(null, "Perizia Medica", "Servizio di Perizia Medica su documentazione",
                new BigDecimal("10.00")));
        Item updated = new Item(item.getId(), "Perizia Medica (updated)",
                "Servizio di Perizia Medica su documentazione (updated)", new BigDecimal("99.00"));
        Optional<Item> result = updateItem.execute(item.getId(), updated);
        assertTrue(result.isPresent());
        assertEquals("Perizia Medica (updated)", result.get().getName());
        assertEquals("Servizio di Perizia Medica su documentazione (updated)", result.get().getDescription());
        assertEquals(new BigDecimal("99.00"), result.get().getPrice());
    }

    @Test
    void shouldReturnEmptyIfItemNotFound() {
        Item notExisting = new Item(null, "Perizia Medica", "Servizio di Perizia Medica su documentazione",
                new BigDecimal("10.00"));
        Optional<Item> result = updateItem.execute(notExisting.getId(), notExisting);
        assertTrue(result.isEmpty());
    }
}
