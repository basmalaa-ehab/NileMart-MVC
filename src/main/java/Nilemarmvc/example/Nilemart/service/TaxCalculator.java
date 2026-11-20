package Nilemarmvc.example.Nilemart.service;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaxCalculator {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.14");

    public BigDecimal calculateTax(BigDecimal subtotal) {
        return subtotal.multiply(TAX_RATE);
    }



}