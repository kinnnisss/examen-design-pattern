package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponse(
        Long id,
        String phoneNumber,
        String email,
        String code,
        String currency,
        BigDecimal balance,
        LocalDateTime createdAt
) {
}