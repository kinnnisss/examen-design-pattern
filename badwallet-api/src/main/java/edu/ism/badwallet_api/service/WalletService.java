package edu.ism.badwallet_api.service;


import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;

public interface WalletService {

    WalletResponse createWallet(CreateWalletRequest request);
}