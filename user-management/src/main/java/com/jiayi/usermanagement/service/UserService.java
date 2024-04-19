package com.jiayi.usermanagement.service;

import com.jiayi.usermanagement.config.JwtService;
import com.jiayi.usermanagement.dto.AuthenticationRequest;
import com.jiayi.usermanagement.dto.AuthenticationResponse;
import com.jiayi.usermanagement.dto.UserRequest;
import com.jiayi.usermanagement.dto.UserResponse;
import com.jiayi.usermanagement.model.*;
import com.jiayi.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(UserRequest userRequest, String userType) {
        User user;

        // Use equals() for string comparison instead of '=='
        if ("Customer".equals(userType)) {
            user = Customer.builder()
                    .userName(userRequest.getUserName())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .email(userRequest.getEmail())
                    .role(Role.CUSTOMER)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    .credits(0) // specific to Customer
                    .build();
        } else if ("Manager".equals(userType)) {
            user = Manager.builder()
                    .userName(userRequest.getUserName())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .email(userRequest.getEmail())
                    .role(Role.MANAGER)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    // Manager specific fields can be initialized here
                    .build();
        } else if ("Admin".equals(userType)) {
            user = Admin.builder()
                    .userName(userRequest.getUserName())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .email(userRequest.getEmail())
                    .role(Role.ADMIN)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    // Admin specific fields can be initialized here
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        userRepository.save(user);
        log.info("Registration successful!");
        // Generate the token
        var jarToken = jwtService.generateToken(user);
        // return the token so there's no need to enter the password and userEmail to authenticate again.
        return AuthenticationResponse.builder()
                .token(jarToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            // Authentication succeeded, proceed with further actions if needed
        } catch (AuthenticationException ex) {
            // Authentication failed, log an error message
            // You can customize the message based on the type of exception if needed
            if (ex instanceof UsernameNotFoundException) {
                // Handle the case where the username is not found
                // Log a specific message for this scenario
                log.error("Username not found: {}", request.getEmail());
            } else {
                // Log a generic message for other authentication failures
                log.error("Authentication failed for user: {}", request.getEmail(), ex);
            }
            // Throw an exception to stop executing code below
            throw ex;
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jarToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jarToken)
                .build();
    }


    private UserResponse buildCustomerResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserResponse getCustomerById(long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        if (user instanceof Admin || user instanceof Manager){
            throw new RuntimeException("The id is not user. It's manager/admin");
        }
        return buildCustomerResponse(user);
    }
}
