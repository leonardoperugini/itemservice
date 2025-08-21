package com.periziafacile.itemservice.presentation;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;

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
        repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Perizia Medica"));
    }

    @Test
    void shouldGetById() throws Exception {
        Item saved = repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));

        mockMvc.perform(get("/items/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Perizia Medica"));
    }

    @Test
    void shouldReturn404ForNonExisting() throws Exception {
        mockMvc.perform(get("/items/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldCreateItem() throws Exception {
        Item item = new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00"));

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Perizia Medica"))
                .andExpect(jsonPath("$.description").value("Perizia Medica su documentazione"))
                .andExpect(jsonPath("$.price").value(89.00));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        Item saved = repository.save(new Item(null, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("89.00")));
        Item updated = new Item(1L, "Perizia Medica (updated)", "Perizia Medica su documentazione (updated)", new BigDecimal("99.00"));

        mockMvc.perform(put("/items/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Perizia Medica (updated)"))
                .andExpect(jsonPath("$.description").value("Perizia Medica su documentazione (updated)"))
                .andExpect(jsonPath("$.price").value(99.00));
    }

    @Test
    void shouldReturn404OnUpdateNonExisting() throws Exception {
        Item updated = new Item(1L, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("99.00"));

        mockMvc.perform(put("/items/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        Item saved = repository.save(new Item(1L, "Perizia Medica", "Perizia Medica su documentazione", new BigDecimal("99.00")));

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