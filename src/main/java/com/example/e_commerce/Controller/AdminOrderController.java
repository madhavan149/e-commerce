package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.Order.OrderResponse;
import com.example.e_commerce.Model.OrderStatus;
import com.example.e_commerce.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> changeStatus(@PathVariable Long id, @RequestParam OrderStatus status){

        return ResponseEntity.ok(orderService.changeStatus(id, status));
    }
}
