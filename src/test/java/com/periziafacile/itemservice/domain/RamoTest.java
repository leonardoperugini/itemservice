package com.periziafacile.itemservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


import com.periziafacile.itemservice.domain.model.Ramo;

class RamoTest {
    
    @Test
    void toStringShouldReturnAllFields() {
        Ramo ramo = new Ramo(10L, "Ramo Test", "Descrizione Ramo Test");
        String result = ramo.toString();
        assertTrue(result.contains("10"));
        assertTrue(result.contains("Ramo Test"));
        assertTrue(result.contains("Descrizione Ramo Test"));
        assertTrue(result.startsWith("Ramo{"));
    }
    @Test
    void shouldCreateRamoWithValidFields() {
        Ramo ramo = new Ramo(1L, "Ramo Casa", "Assicurazione sulla casa");
        assertEquals(1L, ramo.getId());
        assertEquals("Ramo Casa", ramo.getName());
        assertEquals("Assicurazione sulla casa", ramo.getDescription());
    }

    @Test
    void shouldNotAllowNullName() {
        Ramo ramo = new Ramo();
        Exception ex = assertThrows(NullPointerException.class, () -> ramo.setName(null));
        assertNotNull(ex);
    }

    @Test
    void shouldNotAllowNullDescription() {
        Ramo ramo = new Ramo();
        Exception ex = assertThrows(NullPointerException.class, () -> ramo.setDescription(null));
        assertNotNull(ex);
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        Ramo ramo = new Ramo();
        ramo.setId(2L);
        ramo.setName("Ramo Auto");
        ramo.setDescription("Assicurazione sull'auto");

        assertEquals(2L, ramo.getId());
        assertEquals("Ramo Auto", ramo.getName());
        assertEquals("Assicurazione sull'auto", ramo.getDescription());
    }
}