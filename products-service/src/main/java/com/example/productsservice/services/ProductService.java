package com.example.productsservice.services;

import com.example.productsservice.dto.ProductDto;
import com.example.productsservice.feign.clients.CatalogApiClient;
import com.example.productsservice.feign.clients.InventoryApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private CatalogApiClient catalogApiClient;
    private InventoryApiClient inventoryApiClient;

    public Mono<ProductDto> getProductById(final String uniqueId) {
        return catalogApiClient.getProductById(uniqueId)
                .map(productDto -> getInventoryMapAndSetStock(Arrays.asList(productDto)))
                .flatMap(Flux::next);
    }

    public Flux<ProductDto> searchProductsBySku(final String sku) {
        return catalogApiClient.searchProductsBySku(sku)
                .collectList()
                .map(productDtos -> getInventoryMapAndSetStock(productDtos))
                .flatMap(productDtoFlux -> productDtoFlux.collectList())
                .flatMapMany(Flux::fromIterable);
    }

    private Flux<ProductDto> getInventoryMapAndSetStock(final List<ProductDto> productDtos) {
        final List<String> uniqueIdsParam = productDtos.stream().map(ProductDto::getUniqueId).collect(Collectors.toList());
        return inventoryApiClient.getInventoryForProducts(uniqueIdsParam)
                .collectMap(inventory -> inventory.getUniqueId(), inventory -> inventory.getStock())
                .map(inventoryMap -> {
                    productDtos.stream().forEach(productDto -> productDto.setStock(inventoryMap.get(productDto.getUniqueId())));
                    return productDtos;
                })
                .map(products -> filterProductsByStock(productDtos))
                .flatMapMany(Flux::fromIterable);
    }

    private List<ProductDto> filterProductsByStock(final List<ProductDto> productDtos) {
        final List<ProductDto> filteredProducts = productDtos.stream().filter(product -> product.getStock() > 0).collect(Collectors.toList());
        return filteredProducts;
    }

}
