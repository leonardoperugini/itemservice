package com.periziafacile.itemservice.application.usecase;

import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

public class GetItem {
    private final ItemRepository itemRepository;

    public GetItem(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> execute(Long id) {
        return itemRepository.findById(id);
    }
}