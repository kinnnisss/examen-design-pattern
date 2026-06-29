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

    public static Transaction createWithdrawal(
            Wallet wallet,
            BigDecimal amount,
            BigDecimal fee
    ) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setCurrency(wallet.getCurrency());
        transaction.setPaymentMethod("CASH_OUT");
        transaction.setDescription("Retrait depuis le portefeuille");

        return transaction;
    }

    public static Transaction createTransferDebit(
            Wallet sender,
            Wallet receiver,
            BigDecimal amount
    ) {
        Transaction transaction = new Transaction();
        transaction.setWallet(sender);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setFee(BigDecimal.ZERO);
        transaction.setCurrency(sender.getCurrency());
        transaction.setPaymentMethod("WALLET_TRANSFER");
        transaction.setDescription(
                "Transfert envoyé vers le wallet " + receiver.getCode()
        );

        return transaction;
    }

    public static Transaction createTransferCredit(
            Wallet receiver,
            Wallet sender,
            BigDecimal amount
    ) {
        Transaction transaction = new Transaction();
        transaction.setWallet(receiver);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setFee(BigDecimal.ZERO);
        transaction.setCurrency(receiver.getCurrency());
        transaction.setPaymentMethod("WALLET_TRANSFER");
        transaction.setDescription(
                "Transfert reçu depuis le wallet " + sender.getCode()
        );

        return transaction;
    }
    public static Transaction createBillPayment(
        Wallet wallet,
        BigDecimal amount,
        String serviceName,
        String description
) {
    Transaction transaction = new Transaction();
    transaction.setWallet(wallet);
    transaction.setType(TransactionType.BILL_PAYMENT);
    transaction.setAmount(amount);
    transaction.setFee(BigDecimal.ZERO);
    transaction.setCurrency(wallet.getCurrency());
    transaction.setPaymentMethod(serviceName);
    transaction.setDescription(description);

    return transaction;
}
}