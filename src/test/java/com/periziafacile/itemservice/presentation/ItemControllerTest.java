package com.periziafacile.itemservice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryItemRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.findAll().forEach(item -> repository.deleteById(item.getId()));
    }

    @Test
    void shouldListItems() throws Exception {
        repository.save(new Item(null, "Prodotto1", "desc", new BigDecimal("2.00")));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Prodotto1"));
    }

    @Test
    void shouldGetById() throws Exception {
        Item saved = repository.save(new Item(null, "P", "d", new BigDecimal("1.00")));

        mockMvc.perform(get("/items/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("P"));
    }

    @Test
    void shouldReturn404ForNonExisting() throws Exception {
        mockMvc.perform(get("/items/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldCreateItem() throws Exception {
        Item item = new Item(null, "New", "desc", new BigDecimal("9.99"));

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(9.99));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        Item saved = repository.save(new Item(1L, "Old", "desc", new BigDecimal("5.00")));
        Item updated = new Item(1L, "Updated", "changed", new BigDecimal("7.00"));

        mockMvc.perform(put("/items/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.description").value("changed"))
                .andExpect(jsonPath("$.price").value(7.00));
    }

    @Test
    void shouldReturn404OnUpdateNonExisting() throws Exception {
        Item updated = new Item(null, "Updated", "changed", new BigDecimal("7.00"));

        mockMvc.perform(put("/items/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        Item saved = repository.save(new Item(null, "Del", "desc", new BigDecimal("4.00")));

        mockMvc.perform(delete("/items/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404OnDeleteNonExisting() throws Exception {
        mockMvc.perform(delete("/items/88888"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}