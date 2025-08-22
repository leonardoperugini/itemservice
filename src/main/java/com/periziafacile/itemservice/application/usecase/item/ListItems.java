package com.periziafacile.itemservice.application.usecase.item;

import java.util.List;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

public class ListItems {
    private final ItemRepository itemRepository;

    public ListItems(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> execute() {
        return itemRepository.findAll();
    }
}