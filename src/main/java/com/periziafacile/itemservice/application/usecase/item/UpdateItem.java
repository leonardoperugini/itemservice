package com.periziafacile.itemservice.application.usecase.item;

import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;

public class UpdateItem {
    private final ItemRepository itemRepository;

    public UpdateItem(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> execute(Long id, Item updatedItem) {
        return itemRepository.findById(id).map(existing -> {
            existing.setName(updatedItem.getName());
            existing.setDescription(updatedItem.getDescription());
            existing.setPrice(updatedItem.getPrice());
            return itemRepository.save(existing);
        });
    }
}