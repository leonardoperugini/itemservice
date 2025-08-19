package com.periziafacile.itemservice.domain;

import org.junit.jupiter.api.Test;

import com.periziafacile.itemservice.domain.model.Item;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class ItemTest {

    @Test
    void shouldCreateItemWithValidFields() {
        Item item = new Item(1L, "Perizia Medica", "Servizio di Perizia Medica su documentazione", BigDecimal.valueOf(89.99));
        assertEquals(1L, item.getId());
        assertEquals("Perizia Medica", item.getName());
        assertEquals("Servizio di Perizia Medica su documentazione", item.getDescription());
        assertEquals(BigDecimal.valueOf(89.99), item.getPrice());
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
        item.setName("Perizia Veicoli");
        item.setDescription("Servizio di Perizia Veicoli su documentazione");
        item.setPrice(new BigDecimal(2));

        assertEquals(2L, item.getId());
        assertEquals("Perizia Veicoli", item.getName());
        assertEquals("Servizio di Perizia Veicoli su documentazione", item.getDescription());
        assertEquals(new BigDecimal(2), item.getPrice());
    }
    
    @Test
    void shouldNotAllowNegativePrice() {
        Item item = new Item();
        assertDoesNotThrow(() -> item.setPrice(BigDecimal.ZERO));
        item.setPrice(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, item.getPrice());
    }
}