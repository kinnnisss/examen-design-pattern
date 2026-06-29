package edu.ism.badwallet_api.service;

import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.request.DepositRequest;
import edu.ism.badwallet_api.dto.request.WithdrawalRequest;
import edu.ism.badwallet_api.dto.response.DepositResponse;
import edu.ism.badwallet_api.dto.response.WalletBalanceResponse;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import edu.ism.badwallet_api.dto.response.WithdrawalResponse;
import org.springframework.data.domain.Page;

public interface WalletService {

    WalletResponse createWallet(CreateWalletRequest request);

    Page<WalletResponse> getAllWallets(int page, int size);

    WalletResponse getWalletByPhoneNumber(String phoneNumber);

    WalletBalanceResponse getWalletBalance(String phoneNumber);

    DepositResponse deposit(Long walletId, DepositRequest request);

    WithdrawalResponse withdraw(WithdrawalRequest request);
}