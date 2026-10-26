package com.example.e_commerce.config;

import com.example.e_commerce.Exception.CustomAccessDeniedHandler;
import com.example.e_commerce.Service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/product/**", "/api/category/**")
                        .permitAll()

                        .requestMatchers("/api/product/**", "/api/category/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


}