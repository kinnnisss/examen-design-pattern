package edu.ism.badwallet_api.mapper;

import edu.ism.badwallet_api.dto.response.TransactionResponse;
import edu.ism.badwallet_api.entity.Transaction;

public final class TransactionMapper {

    private TransactionMapper() {
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getFee(),
                transaction.getCurrency(),
                transaction.getPaymentMethod(),
                transaction.getDescription(),
                transaction.getCreatedAt()
        );
    }
}