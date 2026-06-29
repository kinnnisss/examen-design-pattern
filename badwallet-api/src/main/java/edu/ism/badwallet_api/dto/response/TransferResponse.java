package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        Long senderTransactionId,
        Long receiverTransactionId,
        String senderPhone,
        String receiverPhone,
        BigDecimal transferredAmount,
        BigDecimal senderNewBalance,
        BigDecimal receiverNewBalance,
        String currency,
        LocalDateTime createdAt
) {
}