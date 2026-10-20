package com.example.e_commerce.Service;

import com.example.e_commerce.DTO.LoginRequest;
import com.example.e_commerce.DTO.RegisterRequest;
import com.example.e_commerce.Exception.ResourceAlreadyExistsException;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Role;
import com.example.e_commerce.Model.Users;
import com.example.e_commerce.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        Users users = new Users();
        users.setName(request.getName());
        users.setEmail(request.getEmail());
        users.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
         users.setRole(Role.CUSTOMER);

        userRepository.save(users);

        return "User registered successfully";

    }

    public String login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        return "Login successful";
    }


}
