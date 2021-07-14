package com.iurilima.transfermoney.controller.payment.business;

import com.iurilima.transfermoney.config.jwt.JwtUtils;
import com.iurilima.transfermoney.controller.payment.CreditCardDto;
import com.iurilima.transfermoney.model.QUser;
import com.iurilima.transfermoney.model.User;
import com.iurilima.transfermoney.repository.UserRepository;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentBusiness {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createPaymentMethod(CreditCardDto creditCardDto, HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
            User user = (User) userRepository.findOne(QUser.user.username.eq(username)).orElseThrow(() -> new UsernameNotFoundException(username));

            PaymentMethodCreateParams.CardDetails creditCardDetails = PaymentMethodCreateParams.CardDetails.builder()
                    .setExpMonth(creditCardDto.getExpMonth())
                    .setExpYear(creditCardDto.getExpYear())
                    .setNumber(creditCardDto.getNumber())
                    .setCvc(creditCardDto.getCvc())
                    .build();

            PaymentMethodCreateParams paymentMethodCreateParams = PaymentMethodCreateParams.builder()
                    .setCard(creditCardDetails)
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .build();

            PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodCreateParams);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", user.getStripeIndentifier());

            paymentMethod.attach(params);
            return ResponseEntity.ok().build();
        } catch(Throwable ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    public ResponseEntity<Object> getPaymentMethods(HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
            User user = (User) userRepository.findOne(QUser.user.username.eq(username)).orElseThrow(() -> new UsernameNotFoundException(username));

            Map<String, Object> params = new HashMap<>();
            params.put("customer", user.getStripeIndentifier());
            params.put("type", "card");

            PaymentMethodCollection paymentMethods = PaymentMethod.list(params);
            return ResponseEntity.ok(paymentMethods.toJson());
        } catch (Throwable ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
