package com.iurilima.transfermoney.controller.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardDto {

    private Long expMonth;
    private Long expYear;
    private String number;
    private String cvc;
}
