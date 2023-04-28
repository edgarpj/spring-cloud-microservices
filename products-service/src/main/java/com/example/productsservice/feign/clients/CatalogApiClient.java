package com.example.productsservice.feign.clients;

import com.example.productsservice.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "catalog-service", url = "http://localhost:9001")
public interface CatalogApiClient {

    @GetMapping(value = "/catalog/products/{uniqueId}")
    Mono<ProductDto> getProductById(@PathVariable("uniqueId") String uniqueId);

    @GetMapping(value = "/catalog/products/search")
    Flux<ProductDto> searchProductsBySku(@RequestParam("sku") String sku);

}
