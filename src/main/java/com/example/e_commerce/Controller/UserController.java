package com.example.e_commerce.Controller;

import com.example.e_commerce.DTO.UserRequest;
import com.example.e_commerce.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@Valid @RequestBody UserRequest request){
        userService.register(request);
    }

    private void login(@Valid @RequestBody UserRequest request){
        userService.login(request);
    }
}
