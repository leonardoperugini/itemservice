package com.periziafacile.itemservice.application.usecase.item;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;

class ListItemsTest {
    private InMemoryItemRepository repository;
    private ListItems listItems;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        listItems = new ListItems(repository);
    }

    @Test
    void shouldReturnAllItems() {
        repository.save(new Item(null, "Perizia Medica", "Servizio di Perizia Medica su documentazione", new BigDecimal("1.00")));
        repository.save(new Item(null, "Perizia Veicoli", "Servizio di Perizia Veicoli su documentazione", new BigDecimal("2.00")));
        assertEquals(2, listItems.execute().size());
    }

    @Test
    void shouldReturnEmptyListIfNoItems() {
        assertTrue(listItems.execute().isEmpty());
    }
}
