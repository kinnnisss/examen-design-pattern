package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositResponse(
        Long transactionId,
        Long walletId,
        String phoneNumber,
        BigDecimal depositedAmount,
        String paymentMethod,
        BigDecimal newBalance,
        String currency,
        LocalDateTime createdAt
) {
}