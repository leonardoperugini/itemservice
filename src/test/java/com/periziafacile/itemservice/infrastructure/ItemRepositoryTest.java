package com.periziafacile.itemservice.infrastructure;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("Salvataggio e recupero di un Item per id")
    void shouldSaveAndFindItemById() {
        Item item = new Item(null, "Penna", "Penna blu", new BigDecimal("2.50"));
        Item saved = itemRepository.save(item);

        Optional<Item> found = itemRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Penna", found.get().getName());
        assertEquals("Penna blu", found.get().getDescription());
        assertEquals(new BigDecimal("2.50"), found.get().getPrice());
    }

    @Test
    @DisplayName("Recupero di un Item inesistente restituisce Optional vuoto")
    void shouldReturnEmptyWhenItemNotFound() {
        Optional<Item> found = itemRepository.findById(123456L);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Recupero di tutti gli Item")
    void shouldFindAllItems() {
        itemRepository.save(new Item(null, "A", "DescA", new BigDecimal("1.00")));
        itemRepository.save(new Item(null, "B", "DescB", new BigDecimal("2.00")));

        List<Item> items = itemRepository.findAll();
        assertTrue(items.size() >= 2);
    }
}