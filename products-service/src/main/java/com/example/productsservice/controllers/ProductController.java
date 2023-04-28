package com.example.productsservice.controllers;

import com.example.productsservice.dto.ProductDto;
import com.example.productsservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/products/{uniqueId}")
    public Mono<ProductDto> getProductById(@PathVariable final String uniqueId) {
        return productService.getProductById(uniqueId);
    }

    @GetMapping("/products/search")
    public Flux<ProductDto> searchProductsBySku(@RequestParam final String sku) {
        return productService.searchProductsBySku(sku);
    }
}
