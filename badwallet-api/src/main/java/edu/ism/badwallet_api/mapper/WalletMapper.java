package edu.ism.badwallet_api.mapper;

import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import edu.ism.badwallet_api.entity.Wallet;
import java.math.BigDecimal;

public final class WalletMapper {

    private WalletMapper() {
    }

    public static Wallet toEntity(CreateWalletRequest request) {
        Wallet wallet = new Wallet();
        wallet.setPhoneNumber(request.phoneNumber());
        wallet.setEmail(request.email());
        wallet.setCode(request.code());
        wallet.setCurrency(request.currency());
        wallet.setBalance(
                request.initialBalance() == null
                        ? BigDecimal.ZERO
                        : request.initialBalance()
        );

        return wallet;
    }

    public static WalletResponse toResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getPhoneNumber(),
                wallet.getEmail(),
                wallet.getCode(),
                wallet.getCurrency(),
                wallet.getBalance(),
                wallet.getCreatedAt()
        );
    }
}