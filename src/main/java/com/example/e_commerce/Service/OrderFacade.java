package com.example.e_commerce.Service;

import com.example.e_commerce.DTO.Order.OrderRequest;
import com.example.e_commerce.DTO.Order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private static final int MAX_ATTEMPTS = 5;


    public OrderResponse placeOrder(Long userId, OrderRequest request) {

        int attempt = 0;

        while (true){

            try {
                return orderService.placeOrder(userId, request);
            }
            catch (ObjectOptimisticLockingFailureException e){

                attempt++;

                if (attempt >= MAX_ATTEMPTS){
                    throw e;
                }

                try {
                    Thread.sleep(50);
                }
                catch (InterruptedException ie){
                    Thread.currentThread().interrupt();
                    throw e;
                }

            }
        }
    }
}
