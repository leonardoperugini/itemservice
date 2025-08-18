package com.periziafacile.itemservice.application.usecase;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemorItemRepository;

class DeleteItemTest {
    private InMemorItemRepository repository;
    private DeleteItem deleteItem;

    @BeforeEach
    void setUp() {
        repository = new InMemorItemRepository();
        deleteItem = new DeleteItem(repository);
    }

    @Test
    void shouldDeleteExistingItem() {
        Item item = repository.save(new Item(null, "ToDelete", "Desc", new BigDecimal("3.00")));
        deleteItem.execute(item.getId());
        assertTrue(repository.findById(item.getId()).isEmpty());
    }

    @Test
    void shouldNotThrowIfItemNotFound() {
        assertDoesNotThrow(() -> deleteItem.execute(999L));
    }
}
