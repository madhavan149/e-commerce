package com.example.e_commerce.Exception;

import com.example.e_commerce.Model.OrderStatus;

public class InvalidStatusTransitionException extends RuntimeException{

    public InvalidStatusTransitionException(
            OrderStatus current,
            OrderStatus next) {

        super("Cannot change status from " + current + " to " + next);
    }
}
