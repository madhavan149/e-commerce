package com.example.e_commerce.Service;

import com.example.e_commerce.DTO.UserRequest;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Users;
import com.example.e_commerce.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(@Valid UserRequest request) {

        Users users = new Users();

        users.setName(request.getName());
        users.setPassword(request.getPassword());

        userRepository.save(users);
    }

    public void login(@Valid UserRequest request) {
        Users users = userRepository.findByName(request.getName())
                .orElseThrow(() -> new ResourceNotFoundException("user name not found"));

        users.setPassword();
    }
}
