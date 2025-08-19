package com.periziafacile.itemservice.application.usecase;

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
        Item item = repository.save(new Item(null, "Old", "OldDesc", new BigDecimal("5.00")));
        Item updated = new Item(item.getId(), "New", "NewDesc", new BigDecimal("10.00"));
        Optional<Item> result = updateItem.execute(item.getId(), updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
        assertEquals("NewDesc", result.get().getDescription());
        assertEquals(new BigDecimal("10.00"), result.get().getPrice());
    }

    @Test
    void shouldReturnEmptyIfItemNotFound() {
        Item notExisting = new Item(999L, "X", "Y", new BigDecimal("1.00"));
        Optional<Item> result = updateItem.execute(notExisting.getId(), notExisting);
        assertTrue(result.isEmpty());
    }
}
