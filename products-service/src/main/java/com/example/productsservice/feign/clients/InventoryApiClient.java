package com.example.productsservice.feign.clients;

import com.example.productsservice.dto.InventoryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.util.List;

@ReactiveFeignClient(name = "inventory-service", url = "http://localhost:9002")
public interface InventoryApiClient {
    @GetMapping(value = "/inventory")
    Flux<InventoryDto> getInventoryForProducts(@RequestParam("uniqueIds") List<String> uniqueIds);
}
