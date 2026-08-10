package com.example.PaymentGatewaySimulator.service;

import com.example.PaymentGatewaySimulator.dto.RegisterRequest;
import com.example.PaymentGatewaySimulator.entity.User;
import com.example.PaymentGatewaySimulator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.PaymentGatewaySimulator.dto.LoginRequest;
import com.example.PaymentGatewaySimulator.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        // 1. Check whether username already exists
        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        // 2. Create User entity
        User user = User.builder()
                .username(request.getUsername())

                // 3. NEVER store plain password
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )

                // 4. Default role
                .role("USER")
                .build();

        // 5. Save user to database
        userRepository.save(user);
    }
    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}