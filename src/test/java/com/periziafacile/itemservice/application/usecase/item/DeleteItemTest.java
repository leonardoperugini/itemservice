package com.periziafacile.itemservice.application.usecase.item;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;

class DeleteItemTest {
    private InMemoryItemRepository repository;
    private DeleteItem deleteItem;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        deleteItem = new DeleteItem(repository);
    }

    @Test
    void shouldDeleteExistingItem() {
        Item item = new Item(null, "Perizia Medica", "Servizio di Perizia Medica su documentazione", new BigDecimal("10.00"));
        deleteItem.execute(item.getId());
        assertTrue(repository.findById(item.getId()).isEmpty());
    }

    @Test
    void shouldNotThrowIfItemNotFound() {
        assertDoesNotThrow(() -> deleteItem.execute(999L));
    }
}
