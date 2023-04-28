package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Inventory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Inventory, String> {
    Flux<Inventory> findInventoriesByUniqueIdIn(List<String> inventoryIdList);
}
