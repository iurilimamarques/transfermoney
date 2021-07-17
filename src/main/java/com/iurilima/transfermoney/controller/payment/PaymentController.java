package com.iurilima.transfermoney.controller.payment;

import com.iurilima.transfermoney.controller.payment.business.PaymentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentBusiness paymentBusiness;

    @PostMapping
    public ResponseEntity<Object> createPaymentMethod(@Valid @RequestBody CreditCardDto creditCardDto, HttpServletRequest request) {
        return paymentBusiness.createPaymentMethod(creditCardDto, request);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<Object> getPaymentMethods(HttpServletRequest request) throws InterruptedException {
        return paymentBusiness.getPaymentMethods(request);
    }
}
