package Nilemarmvc.example.Nilemart.enums;

import java.math.BigDecimal;

public enum Zone { GREATER_CAIRO(new BigDecimal("50"), true),
    ALEX_DELTA(new BigDecimal("65"), true),
    CANAL(new BigDecimal("70"), true),
    UPPER_EGYPT(new BigDecimal("85"), true),
    REMOTE(new BigDecimal("120"), false);

    private final BigDecimal fee;
    private final boolean freeShippingEligible;

    Zone(BigDecimal fee, boolean freeShippingEligible) {
        this.fee = fee;
        this.freeShippingEligible = freeShippingEligible;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public boolean isFreeShippingEligible() {
        return freeShippingEligible;
    }
}
