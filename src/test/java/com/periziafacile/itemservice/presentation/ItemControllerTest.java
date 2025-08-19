package com.periziafacile.itemservice.presentation;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.infrastructure.repository.InMemoryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {

    private MockMvc mockMvc;
    private InMemoryItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        ItemController controller = new ItemController(repository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        repository.save(new Item(null, "Test", "Desc", new BigDecimal("10.00")));
    }

    @Test
    void shouldReturnAllItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test"));
    }
}