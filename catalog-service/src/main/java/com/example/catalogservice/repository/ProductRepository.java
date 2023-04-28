package com.example.catalogservice.repository;

import com.example.catalogservice.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    Flux<Product> findAllBySku(String sku);
}
