package edu.ism.badwallet_api.strategy.impl;

import edu.ism.badwallet_api.strategy.WithdrawalFeeStrategy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class PercentageCappedWithdrawalFeeStrategy implements WithdrawalFeeStrategy {

    private static final BigDecimal FEE_RATE = new BigDecimal("0.01");
    private static final BigDecimal MAX_FEE = new BigDecimal("5000.00");

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        BigDecimal calculatedFee = amount
                .multiply(FEE_RATE)
                .setScale(2, RoundingMode.HALF_UP);

        return calculatedFee.min(MAX_FEE);
    }
}