package com.periziafacile.itemservice.domain.port;

import java.util.List;
import java.util.Optional;

import com.periziafacile.itemservice.domain.model.Item;

public interface ItemRepository {
    Optional<Item> findById(Long id);
    List<Item> findAll();
    Item save(Item item);
    void deleteById(Long id);
}