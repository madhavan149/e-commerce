package com.example.e_commerce.DTO.Order;

import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        OrderStatus status,
        BigDecimal total,
        Instant CreatedAt,
        List<ItemResponse> items) {

    public record ItemResponse(
            Long productId,
            int qyt,
            BigDecimal unitPrice
    ){}

    public static OrderResponse from (Order order){
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(i -> new ItemResponse(i.getProductId(), i.getQty(), i.getUnitPrice()))
                        .toList()
        );
    }


}
