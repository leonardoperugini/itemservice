package com.periziafacile.itemservice.application.usecase;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemorItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetItemTest {
    private InMemorItemRepository repository;
    private GetItem getItem;

    @BeforeEach
    void setUp() {
        repository = new InMemorItemRepository();
        getItem = new GetItem(repository);
    }

    @Test
    void shouldReturnItemById() {
        Item item = repository.save(new Item(null, "Test", "Desc", new BigDecimal("10.00")));
        Optional<Item> found = getItem.execute(item.getId());
        assertTrue(found.isPresent());
        assertEquals(item.getId(), found.get().getId());
    }

    @Test
    void shouldReturnEmptyIfNotFound() {
        Optional<Item> result = getItem.execute(999L);
        assertTrue(result.isEmpty());
    }
}
