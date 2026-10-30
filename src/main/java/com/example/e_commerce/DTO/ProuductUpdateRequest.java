package com.example.e_commerce.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProuductUpdateRequest {

    @Pattern(regexp = ".*\\S.*", message = "Name cannot be blank")
    private String name;

    private String description;

    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price;

    @PositiveOrZero(message = "Stock cannot be negative")
    private Long stockQuantity;

    private Long categoryId;
}
