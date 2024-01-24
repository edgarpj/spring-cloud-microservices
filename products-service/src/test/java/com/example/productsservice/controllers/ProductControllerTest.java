package com.example.productsservice.controllers;

import com.example.productsservice.dto.ProductDto;
import com.example.productsservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    void testGetProductById() {
        // Mock the ProductService response
        final ProductDto productDto = ProductDto.builder()
                .uniqueId("1")
                .title("Sample Product")
                .build();
        when(productService.getProductById(anyString()))
                .thenReturn(Mono.just(productDto));

        // Perform the web test
        webTestClient.get()
                .uri("/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class).isEqualTo(productDto);
    }

    @Test
    void testSearchProductsBySku() {
        // Mock the ProductService response
        final ProductDto productDto = ProductDto.builder()
                .uniqueId("1")
                .sku("sample_sku")
                .title("Sample Product")
                .category("sample_category")
                .build();
        when(productService.searchProductsBySku(anyString()))
                .thenReturn(Flux.just(productDto));

        // Perform the web test
        webTestClient.get()
                .uri("/products/search?sku=sample_sku")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductDto.class).hasSize(1)
                .contains(productDto);
    }
}
