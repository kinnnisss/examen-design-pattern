package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExternalFacturePaymentResponse(
        String walletCode,
        String serviceName,
        List<String> paidFactureReferences,
        BigDecimal totalPaid,
        LocalDateTime paidAt
) {
}