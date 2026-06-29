package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BillPaymentResponse(
        Long transactionId,
        String phoneNumber,
        String walletCode,
        String serviceName,
        List<String> paidFactureReferences,
        BigDecimal totalPaid,
        BigDecimal newBalance,
        String currency,
        LocalDateTime paidAt
) {
}