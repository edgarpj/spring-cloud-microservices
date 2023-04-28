package com.example.catalogservice.controllers;

import com.example.catalogservice.model.Product;
import com.example.catalogservice.services.ProductsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
public class ProductsController {
    public static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    private ProductsService productsService;

    @GetMapping("/catalog/products/{uniqueId}")
    public Mono<Product> getProduct(@PathVariable final String uniqueId) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3);
        return productsService.getProductByUniqueId(uniqueId).doFirst(() -> LOG.info("Returning product from catalog"));
    }

    @GetMapping("/catalog/products/search")
    public Flux<Product> searchProducts(@RequestParam final String sku) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3);
        return productsService.searchProducts(sku).doFirst(() -> LOG.info("Returning list of products from catalog"));
    }

    @PostMapping("/catalog/products")
    public Mono<Product> searchProducts() {
        final Product product = new Product();
        product.setUniqueId("testId2");
        product.setSku("testSku");
        product.setTitle("Test Title");
        product.setCategory("Test Category");
        product.setDescription("Test Description");
        product.setPrice(10.50);

        return productsService.saveProduct(product);
    }

}
