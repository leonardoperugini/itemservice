package com.periziafacile.itemservice.application.usecase;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

public class CreateItem {
    private final ItemRepository itemRepository;

    public CreateItem(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item execute(Item item) {
        return itemRepository.save(item);
    }
}