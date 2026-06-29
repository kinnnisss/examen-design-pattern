package edu.ism.badwallet_api.service;

import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import org.springframework.data.domain.Page;

public interface WalletService {

    WalletResponse createWallet(CreateWalletRequest request);

    Page<WalletResponse> getAllWallets(int page, int size);

    WalletResponse getWalletByPhoneNumber(String phoneNumber);
}