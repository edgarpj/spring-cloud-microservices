package com.example.productsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private String uniqueId;
    private String sku;
    private String title;
    private String description;
    private Double price;
    private String category;
    private Integer stock;
}
