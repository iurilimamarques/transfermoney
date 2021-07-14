package com.iurilima.transfermoney.controller.payment;

import com.iurilima.transfermoney.controller.payment.business.PaymentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentBusiness paymentBusiness;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<Object> createPaymentMethod(@Valid @RequestBody CreditCardDto creditCardDto, HttpServletRequest request) {
        return paymentBusiness.createPaymentMethod(creditCardDto, request);
    }

    @GetMapping
    public ResponseEntity<Object> getPaymentMethods(HttpServletRequest request) {
        return paymentBusiness.getPaymentMethods(request);
    }
}
