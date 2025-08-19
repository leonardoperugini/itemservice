package it.leonardoperugini.itemservice.infrastructure;

import it.leonardoperugini.itemservice.domain.Item;
import it.leonardoperugini.itemservice.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryJpaTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void shouldSaveAndFindItemById() {
        Item item = new Item("Perizia Immobili", "Servizio di Perizia Immobili su documentazione", 89.99);
        itemRepository.save(item);

        Optional<Item> found = itemRepository.findById("Perizia Immobili");
        assertTrue(found.isPresent());
        assertEquals("Perizia Immobili", found.get().getName());
    }
}