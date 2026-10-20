package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.RegisterRequest;
import com.example.e_commerce.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public void register(@RequestBody RegisterRequest request){
        authService.register(request);
    }


}
