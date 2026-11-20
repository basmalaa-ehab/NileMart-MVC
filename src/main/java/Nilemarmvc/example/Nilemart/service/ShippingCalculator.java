package Nilemarmvc.example.Nilemart.service;

import Nilemarmvc.example.Nilemart.enums.Governorate;

import java.math.BigDecimal;

public interface ShippingCalculator {
    BigDecimal calculate(Governorate governorate);
}