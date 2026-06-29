package edu.ism.badwallet_api.service.impl;

import edu.ism.badwallet_api.entity.DepositPaymentMethod;
import edu.ism.badwallet_api.entity.Transaction;
import edu.ism.badwallet_api.entity.Wallet;
import edu.ism.badwallet_api.factory.TransactionFactory;
import edu.ism.badwallet_api.repository.TransactionRepository;
import edu.ism.badwallet_api.repository.WalletRepository;
import edu.ism.badwallet_api.service.WalletSeedService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletSeedServiceImpl implements WalletSeedService {

    private static final String COUNTRY_CODE = "+22177";

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

@Override
@Async("walletSeedExecutor")
@Transactional
public void seedWalletsAsync(int numWallets, int eventsPerWallet) {
    log.info(
            "Début du seeding : {} wallets, {} événements par wallet.",
            numWallets,
            eventsPerWallet
    );

    int generatedTransactions = 0;

    for (int index = 1; index <= numWallets; index++) {
        Wallet wallet = getOrCreateWallet(index);

        for (int eventIndex = 0; eventIndex < eventsPerWallet; eventIndex++) {
            generateRandomEvent(wallet);
            generatedTransactions++;
        }
    }

    log.info(
            "Fin du seeding : {} transactions générées pour {} wallets demandés.",
            generatedTransactions,
            numWallets
    );
}
    private Wallet getOrCreateWallet(int index) {
        String phoneNumber = generatePhoneNumber(index);

        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> {
                    Wallet wallet = new Wallet();
                    wallet.setPhoneNumber(phoneNumber);
                    wallet.setEmail("wallet" + index + "@badwallet.test");
                    wallet.setCode(String.format("WLT-%07d", index));
                    wallet.setCurrency("XOF");
                    wallet.setBalance(randomAmount(20_000, 100_000));

                    return walletRepository.save(wallet);
                });
    }

    private void generateRandomEvent(Wallet wallet) {
        boolean shouldDeposit = wallet.getBalance().compareTo(new BigDecimal("5000.00")) < 0
                || ThreadLocalRandom.current().nextBoolean();

        if (shouldDeposit) {
            createRandomDeposit(wallet);
        } else {
            createRandomWithdrawal(wallet);
        }
    }

    private void createRandomDeposit(Wallet wallet) {
        BigDecimal amount = randomAmount(1_000, 20_000);

        wallet.setBalance(wallet.getBalance().add(amount));

        Transaction transaction = TransactionFactory.createDeposit(
                wallet,
                amount,
                DepositPaymentMethod.CREDIT_CARD
        );

        transactionRepository.save(transaction);
    }

    private void createRandomWithdrawal(Wallet wallet) {
        BigDecimal maximumWithdrawal = wallet.getBalance()
                .multiply(new BigDecimal("0.70"))
                .setScale(2, RoundingMode.HALF_UP);

        if (maximumWithdrawal.compareTo(new BigDecimal("100.00")) < 0) {
            createRandomDeposit(wallet);
            return;
        }

        BigDecimal amount = randomAmount(
                100,
                maximumWithdrawal.intValue()
        );

        BigDecimal fee = amount
                .multiply(new BigDecimal("0.01"))
                .min(new BigDecimal("5000.00"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDebited = amount.add(fee);

        if (wallet.getBalance().compareTo(totalDebited) < 0) {
            createRandomDeposit(wallet);
            return;
        }

        wallet.setBalance(wallet.getBalance().subtract(totalDebited));

        Transaction transaction = TransactionFactory.createWithdrawal(
                wallet,
                amount,
                fee
        );

        transactionRepository.save(transaction);
    }

    private String generatePhoneNumber(int index) {
        return COUNTRY_CODE + String.format("%07d", index);
    }

    private BigDecimal randomAmount(int min, int max) {
        int value = ThreadLocalRandom.current().nextInt(min, max + 1);

        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP);
    }
}