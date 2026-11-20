package Nilemarmvc.example.Nilemart.service;

import Nilemarmvc.example.Nilemart.enums.Governorate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShippingQuoteService {

    private final ShippingCalculator zoneCalculator;
    private final ShippingCalculator freeCalculator;
    private final BigDecimal freeShippingThreshold;

    public ShippingQuoteService(
            ShippingCalculator zoneCalculator,
            @Qualifier("freeShipping") ShippingCalculator freeCalculator) {

        this.zoneCalculator = zoneCalculator;
        this.freeCalculator = freeCalculator;
        this.freeShippingThreshold = BigDecimal.valueOf(10);
    }

    public BigDecimal quote(Governorate governorate, BigDecimal subtotal) {

        if (subtotal.compareTo(freeShippingThreshold) >= 0
                && governorate.getZone().isFreeShippingEligible()) {
            return freeCalculator.calculate(governorate);
        }

        return zoneCalculator.calculate(governorate);
    }
}