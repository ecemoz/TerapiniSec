package com.yildiz.terapinisec.security;


import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain



}