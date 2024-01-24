package com.example.productsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDto {

    private String uniqueId;
    private Integer stock;

}
