package com.periziafacile.itemservice.application.usecase.item;

import com.periziafacile.itemservice.domain.port.ItemRepository;

public class DeleteItem {
    private final ItemRepository itemRepository;

    public DeleteItem(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void execute(Long id) {
        itemRepository.deleteById(id);
    }
}