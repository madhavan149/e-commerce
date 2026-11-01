package com.example.e_commerce.Exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(Long productId) {
        super("Not enough stock for product " + productId);
    }
}
