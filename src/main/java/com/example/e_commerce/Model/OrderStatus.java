package com.example.e_commerce.Model;

public enum OrderStatus {

    CREATED,
    CONFIRMED,
    CANCELLED,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED;

    public boolean canMoveTo(OrderStatus next) {
        return switch (this) {
            case CREATED          -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED        -> next == SHIPPED || next == CANCELLED;
            case SHIPPED          -> next == OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false;   // final states
        };
    }
}
