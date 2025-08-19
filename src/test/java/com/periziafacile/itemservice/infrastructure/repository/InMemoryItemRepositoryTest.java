package com.periziafacile.itemservice.infrastructure.repository;

import com.periziafacile.itemservice.domain.model.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryItemRepositoryTest {

    private InMemoryItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
    }

    @Test
    void shouldSaveAndFindItemById() {
        Item item = new Item(null, "Penna", "Penna blu", new BigDecimal("2.50"));
        Item saved = repository.save(item);

        Optional<Item> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Penna", found.get().getName());
        assertEquals("Penna blu", found.get().getDescription());
        assertEquals(new BigDecimal("2.50"), found.get().getPrice());
    }

    @Test
    void shouldReturnEmptyWhenItemNotFound() {
        Optional<Item> found = repository.findById(999L);
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        repository.save(new Item(null, "A", "DescA", new BigDecimal("1.00")));
        repository.save(new Item(null, "B", "DescB", new BigDecimal("2.00")));

        List<Item> items = repository.findAll();
        assertEquals(2, items.size());
    }

    @Test
    void shouldDeleteById() {
        Item item = repository.save(new Item(null, "Penna", "Penna blu", new BigDecimal("2.50")));
        repository.deleteById(item.getId());

        Optional<Item> found = repository.findById(item.getId());
        assertTrue(found.isEmpty());
    }
}