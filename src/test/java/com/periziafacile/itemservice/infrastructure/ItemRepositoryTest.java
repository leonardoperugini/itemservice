package com.periziafacile.itemservice.infrastructure;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void shouldSaveAndFindItemById() {
        Item item = new Item(1L, "Perizia Immobili", "Servizio di Perizia Immobili su documentazione", BigDecimal.valueOf(89.99));
        itemRepository.save(item);

        Optional<Item> found = itemRepository.findById(item.getId());
        assertTrue(found.isPresent());
        assertEquals("Perizia Immobili", found.get().getName());
        assertEquals("Servizio di Perizia Immobili su documentazione", found.get().getDescription());
        assertEquals(BigDecimal.valueOf(89.99), found.get().getPrice());
    }
}