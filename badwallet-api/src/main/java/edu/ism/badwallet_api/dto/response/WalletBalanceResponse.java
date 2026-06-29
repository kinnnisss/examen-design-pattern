package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;

public record WalletBalanceResponse(
        String phoneNumber,
        String code,
        String currency,
        BigDecimal balance
) {
}