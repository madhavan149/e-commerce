package com.example.e_commerce.Service;

import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.CustomUserDetail;
import com.example.e_commerce.Model.Users;
import com.example.e_commerce.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found" +  username));

        return new CustomUserDetail(users);
    }
}
