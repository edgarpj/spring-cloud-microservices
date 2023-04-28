package com.example.catalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("product")
@Getter
@Setter
public class Product implements Persistable<String> {
    @Id
    @Column("unique_id")
    private String uniqueId;
    private String sku;
    private String title;
    private String description;
    private Double price;
    private String category;
    private LocalDateTime created;

    private LocalDateTime updated;

    @Override
    public String getId() {
        return uniqueId;
    }

    @Override
    public boolean isNew() {
        return created == null;
    }
}
