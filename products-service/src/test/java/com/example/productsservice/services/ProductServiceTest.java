package com.example.productsservice.services;

import com.example.productsservice.dto.InventoryDto;
import com.example.productsservice.dto.ProductDto;
import com.example.productsservice.feign.clients.CatalogApiClient;
import com.example.productsservice.feign.clients.InventoryApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private CatalogApiClient catalogApiClient;

    @Mock
    private InventoryApiClient inventoryApiClient;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProductById() {
        // Given
        when(catalogApiClient.getProductById("1"))
                .thenReturn(Mono.just(ProductDto.builder()
                        .uniqueId("1")
                        .title("Sample Product")
                        .build()));

        when(inventoryApiClient.getInventoryForProducts(anyList()))
                .thenReturn(Flux.just(InventoryDto.builder()
                        .uniqueId("1")
                        .stock(10)
                        .build()));

        // When
        Mono<ProductDto> result = productService.getProductById("1");

        // Then
        StepVerifier.create(result)
                .consumeNextWith(productDto -> {
                    assertNotNull(productDto);
                    assertEquals("1", productDto.getUniqueId());
                    assertEquals("Sample Product", productDto.getTitle());
                    assertEquals(10, productDto.getStock());
                })
                .verifyComplete();

        verify(catalogApiClient, times(1)).getProductById("1");
        verify(inventoryApiClient, times(1)).getInventoryForProducts(Collections.singletonList("1"));
    }

    @Test
    void testSearchProductsBySku() {
        // Given
        when(catalogApiClient.searchProductsBySku("sample_sku"))
                .thenReturn(Flux.just(ProductDto.builder()
                        .uniqueId("1")
                        .title("Sample Product")
                        .build()));

        when(inventoryApiClient.getInventoryForProducts(anyList()))
                .thenReturn(Flux.just(InventoryDto.builder()
                        .uniqueId("1")
                        .stock(10)
                        .build()));

        // When
        Flux<ProductDto> result = productService.searchProductsBySku("sample_sku");

        StepVerifier.create(result)
                .expectNextMatches(productDto -> {
                    assertNotNull(productDto);
                    assertEquals("1", productDto.getUniqueId());
                    assertEquals("Sample Product", productDto.getTitle());
                    assertEquals(10, productDto.getStock());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(catalogApiClient, times(1)).searchProductsBySku("sample_sku");
        verify(inventoryApiClient, times(1)).getInventoryForProducts(Collections.singletonList("1"));
    }
}
