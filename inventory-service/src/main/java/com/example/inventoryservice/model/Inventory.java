package com.example.inventoryservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("inventory")
@Getter
@Setter
public class Inventory implements Persistable<String> {

    @Id
    @Column("unique_id")
    private String uniqueId;
    private Integer stock;

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
