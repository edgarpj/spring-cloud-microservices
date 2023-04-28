package com.example.inventoryservice.services;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class InventoryService {

    private InventoryRepository inventoryRepository;

    public Flux<Inventory> getInventoryForProducts(final List<String> uniqueIds) {
        return inventoryRepository.findInventoriesByUniqueIdIn(uniqueIds);
    }

    public Mono<Inventory> createInventory(final Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}
