package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String type,
        BigDecimal amount,
        BigDecimal fee,
        String currency,
        String paymentMethod,
        String description,
        LocalDateTime createdAt
) {
}