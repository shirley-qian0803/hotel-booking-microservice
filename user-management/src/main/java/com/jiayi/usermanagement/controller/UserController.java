package com.jiayi.usermanagement.controller;

import com.jiayi.usermanagement.dto.AuthenticationRequest;
import com.jiayi.usermanagement.dto.AuthenticationResponse;
import com.jiayi.usermanagement.dto.UserRequest;
import com.jiayi.usermanagement.dto.UserResponse;
import com.jiayi.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/customer/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody UserRequest userRequest) {
        AuthenticationResponse res = userService.register(userRequest, "Customer");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody UserRequest userRequest) {
        AuthenticationResponse res = userService.register(userRequest, "Admin");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/manager/register")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestBody UserRequest userRequest) {
        AuthenticationResponse res = userService.register(userRequest, "Manager");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginCustomer(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(userService.authenticate(authenticationRequest), HttpStatus.OK);
    }


    @GetMapping("/customer/{userId}")
    public ResponseEntity<UserResponse> getCustomer(@PathVariable long userId){
        UserResponse customer = userService.getCustomerById(userId);
        if (customer == null) {
            return ResponseEntity.notFound().build();  // Return 404 if the customer is not found
        }
        return ResponseEntity.ok(customer);  // Return 200 OK with the customer data
    }
    
}
