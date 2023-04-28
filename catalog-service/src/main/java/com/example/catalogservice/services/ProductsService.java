package com.example.catalogservice.services;

import com.example.catalogservice.model.Product;
import com.example.catalogservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@AllArgsConstructor
public class ProductsService {

    private ProductRepository productRepository;

    public Mono<Product> getProductByUniqueId(final String uniqueId) {
        return productRepository.findById(uniqueId);
    }

    public Flux<Product> searchProducts(final String sku) {
        return productRepository.findAllBySku(sku);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> saveProduct(final Product product) {
        return productRepository.save(product);
    }

    public void removeAllProducts() {
        productRepository.deleteAll();
    }
}
