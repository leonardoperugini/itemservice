package com.periziafacile.itemservice.application.usecase;

import org.junit.jupiter.api.BeforeEach;

import com.periziafacile.itemservice.infrastructure.repository.InMemorItemRepository;

class GetItemTest {
    private InMemorItemRepository repository;
    private GetItem getItem;

    @BeforeEach
    void setUp() {
        repository = new InMemorItemRepository();
        getItem = new GetItem(repository);
    }

    
}
