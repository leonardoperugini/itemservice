package com.periziafacile.itemservice.infrastructure.repository;

import com.periziafacile.itemservice.domain.model.Item;
import com.periziafacile.itemservice.domain.port.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item = new Item(idSequence++, item.getName(), item.getDescription(), item.getPrice());
        }
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteById(Long id) {
        items.remove(id);
    }
}