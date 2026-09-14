package com.example.customer_service.controllers;

import com.example.customer_service.dto.LoginRequest;
import com.example.customer_service.model.Customer;
import com.example.customer_service.security.JwtService;
import com.example.customer_service.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(CustomerService customerService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Customer> customerOpt = customerService.findByEmailOptional(loginRequest.email());
        if(customerOpt.isEmpty() || !passwordEncoder.matches(loginRequest.password(), customerOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("Fel email eller lösenord");
        }

        Customer customer = customerOpt.get();
        String token = jwtService.generateToken(customer.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
