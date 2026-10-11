package com.example.e_commerce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductResponse {

        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Long stockQuantity;
        private String categoryName;

}
