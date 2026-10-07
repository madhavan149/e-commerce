package com.example.e_commerce.Model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private String descrpition;

    private BigDecimal price;

    private Long stock_quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
