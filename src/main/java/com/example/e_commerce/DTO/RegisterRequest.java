package com.example.e_commerce.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
}
