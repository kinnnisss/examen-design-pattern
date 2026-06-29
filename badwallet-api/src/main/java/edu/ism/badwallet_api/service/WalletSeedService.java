package edu.ism.badwallet_api.service;

public interface WalletSeedService {

    void seedWalletsAsync(int numWallets, int eventsPerWallet);
}