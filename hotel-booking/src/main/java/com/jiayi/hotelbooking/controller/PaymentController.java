package com.jiayi.hotelbooking.controller;

import com.jiayi.hotelbooking.dto.StripeChargeDto;
import com.jiayi.hotelbooking.dto.StripeTokenDto;
import com.jiayi.hotelbooking.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody StripeTokenDto stripeTokenDto) {
        // 还需要构思一下
        // 1. 先createCardToken 2. 再charge
        return new ResponseEntity<>("Payment confirmed and email sent.", HttpStatus.OK);
    }
}