package com.example.productsservice.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String uniqueId;
    private String sku;
    private String title;
    private String description;
    private Double price;
    private String category;
    private Integer stock;
}
