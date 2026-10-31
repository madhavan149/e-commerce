package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.Order.OrderRequest;
import com.example.e_commerce.DTO.Order.OrderResponse;
import com.example.e_commerce.Repository.UserRepository;
import com.example.e_commerce.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }


    @PostMapping
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request, Authentication auth){

        Long userId = userRepository.findByName(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .getId();

        return orderService.placeOrder(userId, request);
    }
}
