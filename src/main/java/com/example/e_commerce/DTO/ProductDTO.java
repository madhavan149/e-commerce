package com.example.e_commerce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {

    private String name;

    private String descrpition;

    private BigDecimal price;

    private Long stock_quantity;

    private Long category_id;
}
