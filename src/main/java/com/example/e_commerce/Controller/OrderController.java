package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.Order.OrderRequest;
import com.example.e_commerce.DTO.Order.OrderResponse;
import com.example.e_commerce.Repository.UserRepository;
import com.example.e_commerce.Service.OrderFacade;
import com.example.e_commerce.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final OrderFacade orderFacade;

    @PostMapping
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request, Authentication auth){

        return orderFacade.placeOrder(UserId(auth), request);
    }

    @GetMapping("/{id}")
   public OrderResponse getMyOrder(@PathVariable Long id, Authentication auth){
        return orderService.getMyOrder(id, UserId(auth));
   }

   private Long UserId(Authentication auth){

        return userRepository.findByName(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .getId();
   }
}
