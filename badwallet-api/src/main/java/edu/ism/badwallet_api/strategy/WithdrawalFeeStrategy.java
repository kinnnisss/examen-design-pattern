package edu.ism.badwallet_api.strategy;

import java.math.BigDecimal;

public interface WithdrawalFeeStrategy {

    BigDecimal calculateFee(BigDecimal amount);
}