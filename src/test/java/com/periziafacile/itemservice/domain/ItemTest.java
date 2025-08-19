package it.leonardoperugini.itemservice.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void shouldCreateItemWithValidFields() {
        Item item = new Item("Perizia Medica", "Servizio di Perizia Medica su documentazione", 89.99);
        assertEquals("Perizia Medica", item.getId());
        assertEquals("Servizio di Perizia Medica su documentazione", item.getName());
        assertEquals(89.99, item.getPrice());
    }

    @Test
    void shouldNotAllowNullName() {
        Item item = new Item();
        Exception ex = assertThrows(NullPointerException.class, () -> item.setName(null));
        assertNotNull(ex);
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        Item item = new Item();
        item.setId(2L);
        item.setName("Penna");
        item.setDescription("Blu");
        item.setPrice(new BigDecimal("2.00"));

        assertEquals(2L, item.getId());
        assertEquals("Penna", item.getName());
        assertEquals("Blu", item.getDescription());
        assertEquals(new BigDecimal("2.00"), item.getPrice());
    }

    @Test
    void shouldNotAllowNegativePrice() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Item("Perizia Veicoli", "Servizio di Perizia Veicoli su documentazione", -59.99));
        assertTrue(ex.getMessage().contains("price"));
    }
}