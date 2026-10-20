package com.example.e_commerce.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
