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
import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryRamoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class RamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryRamoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.findAll().forEach(ramo -> repository.deleteById(ramo.getId()));
    }

    @Test
    void shouldListRami() throws Exception {
        repository.save(new Ramo(null, "Ramo Vita", "Assicurazione sulla vita"));

        mockMvc.perform(get("/rami"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ramo Vita"));
    }

    @Test
    void shouldGetById() throws Exception {
        Ramo saved = repository.save(new Ramo(null, "Ramo Vita", "Assicurazione sulla vita"));

        mockMvc.perform(get("/rami/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ramo Vita"));
    }

    @Test
    void shouldReturn404ForNonExisting() throws Exception {
        mockMvc.perform(get("/rami/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldCreateRamo() throws Exception {
        Ramo ramo = new Ramo(null, "Ramo Vita", "Assicurazione sulla vita");

        mockMvc.perform(post("/rami")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ramo)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Ramo Vita"))
                .andExpect(jsonPath("$.description").value("Assicurazione sulla vita"));
    }

    @Test
    void shouldUpdateRamo() throws Exception {
        Ramo saved = repository.save(new Ramo(null, "Ramo Vita", "Assicurazione sulla vita"));
        Ramo updated = new Ramo(1L, "Ramo Vita (updated)", "Assicurazione sulla vita (updated)");

        mockMvc.perform(put("/rami/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ramo Vita (updated)"))
                .andExpect(jsonPath("$.description").value("Assicurazione sulla vita (updated)"));
    }

    @Test
    void shouldReturn404OnUpdateNonExisting() throws Exception {
        Ramo updated = new Ramo(1L, "Ramo Vita", "Assicurazione sulla vita");

        mockMvc.perform(put("/rami/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldDeleteRamo() throws Exception {
        Ramo saved = repository.save(new Ramo(1L, "Ramo Vita", "Assicurazione sulla vita"));

        mockMvc.perform(delete("/rami/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404OnDeleteNonExisting() throws Exception {
        mockMvc.perform(delete("/rami/88888"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
