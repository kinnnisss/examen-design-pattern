package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExternalFactureResponse(
        String reference,
        String walletCode,
        String serviceName,
        String unite,
        BigDecimal amount,
        String status,
        LocalDate billingMonth,
        LocalDate dueDate,
        LocalDateTime paidAt
) {
}