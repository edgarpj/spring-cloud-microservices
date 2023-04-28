package com.example.inventoryservice.controllers;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.services.InventoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class InventoryController {

    public static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);

    private InventoryService inventoryService;

    @GetMapping("/inventory")
    public Flux<Inventory> getInventoryForProducts(@RequestParam final List<String> uniqueIds) {
        return inventoryService.getInventoryForProducts(uniqueIds).doFirst(() -> LOG.info("Returning list of inventory for given products"));
    }
}
