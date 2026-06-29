package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawalResponse(
        Long transactionId,
        Long walletId,
        String phoneNumber,
        BigDecimal withdrawnAmount,
        BigDecimal fee,
        BigDecimal totalDebited,
        BigDecimal newBalance,
        String currency,
        LocalDateTime createdAt
) {
}