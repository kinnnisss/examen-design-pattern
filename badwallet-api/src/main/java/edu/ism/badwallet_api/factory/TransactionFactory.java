package edu.ism.badwallet_api.factory;

import edu.ism.badwallet_api.entity.DepositPaymentMethod;
import edu.ism.badwallet_api.entity.Transaction;
import edu.ism.badwallet_api.entity.TransactionType;
import edu.ism.badwallet_api.entity.Wallet;
import java.math.BigDecimal;

public final class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction createDeposit(
            Wallet wallet,
            BigDecimal amount,
            DepositPaymentMethod paymentMethod
    ) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setFee(BigDecimal.ZERO);
        transaction.setCurrency(wallet.getCurrency());
        transaction.setPaymentMethod(paymentMethod.name());
        transaction.setDescription("Dépôt sur le portefeuille");

        return transaction;
    }
}