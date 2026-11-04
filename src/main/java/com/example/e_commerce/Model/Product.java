package com.example.e_commerce.Model;


import com.example.e_commerce.Exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Version
    private Long version;

    public void reserve(long qty) {
        if (stockQuantity == null || stockQuantity < qty) {
            throw new OutOfStockException(id);
        }
        stockQuantity -= qty;
    }

    public void restock(long qty) {
        if (stockQuantity == null) {
            stockQuantity = 0L;
        }
        stockQuantity += qty;
    }

}
