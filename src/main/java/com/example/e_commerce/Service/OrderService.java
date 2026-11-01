package com.example.e_commerce.Service;

import com.example.e_commerce.DTO.Order.OrderRequest;
import com.example.e_commerce.DTO.Order.OrderResponse;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.OrderItem;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Repository.OrderRepository;
import com.example.e_commerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse placeOrder(Long userId, OrderRequest request){
        Order order = new Order();
        order.setUserId(userId);

        BigDecimal total = BigDecimal.ZERO;

        for(OrderRequest.Item line : request.items()){
            Product product = productRepository.findById(line.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("product not found"));

            OrderItem item = new OrderItem();

            item.setProductId(product.getId());
            item.setQty(line.qty());
            item.setUnitPrice(product.getPrice());
            order.addItem(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(line.qty())));


        }

        order.setTotal(total);
        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    public OrderResponse getMyOrder(Long id, Long userId) {

        return orderRepository.findByIdAndUserId(id, userId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("order not found"));
    }
}
