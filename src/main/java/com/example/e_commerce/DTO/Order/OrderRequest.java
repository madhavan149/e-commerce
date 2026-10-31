package com.example.e_commerce.DTO.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotEmpty @Valid List<Item> items) {

    public record Item(
            @NotNull(message = "productId is required")
            Long productId,

            @Min(value = 1, message = "qty must be at least 1")
            int qty) {}
}
