package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.LoginRequest;
import com.example.e_commerce.DTO.RegisterRequest;
import com.example.e_commerce.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request){

        return  authService.register(request);

    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }


}
