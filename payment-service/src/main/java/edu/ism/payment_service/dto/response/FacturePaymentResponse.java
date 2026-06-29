package edu.ism.payment_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FacturePaymentResponse(
        String walletCode,
        String serviceName,
        List<String> paidFactureReferences,
        BigDecimal totalPaid,
        LocalDateTime paidAt) {
}
