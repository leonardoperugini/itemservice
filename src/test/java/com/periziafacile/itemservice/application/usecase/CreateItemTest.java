package com.periziafacile.itemservice.application.usecase;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;

class CreateItemTest {
    private InMemoryItemRepository repository;
    private CreateItem createItem;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        createItem = new CreateItem(repository);
    }

    @Test
    void shouldCreateNewItem() {
        Item item = new Item(null, "Test", "Desc", new BigDecimal("10.00"));
        Item saved = createItem.execute(item);
        assertNotNull(saved.getId());
        assertEquals("Test", saved.getName());
        assertEquals("Desc", saved.getDescription());
        assertEquals(new BigDecimal("10.00"), saved.getPrice());
        assertTrue(repository.findById(saved.getId()).isPresent());
    }
}
