package edu.ism.badwallet_api.service.impl;
import edu.ism.badwallet_api.exception.ResourceNotFoundException;
import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import edu.ism.badwallet_api.entity.Wallet;
import edu.ism.badwallet_api.exception.BusinessException;
import edu.ism.badwallet_api.exception.ResourceNotFoundException;
import edu.ism.badwallet_api.mapper.WalletMapper;
import edu.ism.badwallet_api.repository.WalletRepository;
import edu.ism.badwallet_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.ism.badwallet_api.dto.response.WalletBalanceResponse;
import edu.ism.badwallet_api.dto.request.DepositRequest;
import edu.ism.badwallet_api.dto.response.DepositResponse;
import edu.ism.badwallet_api.entity.Transaction;
import edu.ism.badwallet_api.exception.ResourceNotFoundException;
import edu.ism.badwallet_api.factory.TransactionFactory;
import edu.ism.badwallet_api.repository.TransactionRepository;
import edu.ism.badwallet_api.dto.request.WithdrawalRequest;
import edu.ism.badwallet_api.dto.response.WithdrawalResponse;
import edu.ism.badwallet_api.strategy.WithdrawalFeeStrategy;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
private final WithdrawalFeeStrategy withdrawalFeeStrategy;
    @Override
@Transactional(readOnly = true)
public WalletBalanceResponse getWalletBalance(String phoneNumber) {
    Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Aucun portefeuille trouvé pour le numéro : " + phoneNumber
            ));

    return new WalletBalanceResponse(
            wallet.getPhoneNumber(),
            wallet.getCode(),
            wallet.getCurrency(),
            wallet.getBalance()
    );
}
    @Override
    public WalletResponse createWallet(CreateWalletRequest request) {
        validateUniqueness(request);

        Wallet wallet = WalletMapper.toEntity(request);
        Wallet savedWallet = walletRepository.save(wallet);

        return WalletMapper.toResponse(savedWallet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletResponse> getAllWallets(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return walletRepository
                .findAll(pageable)
                .map(WalletMapper::toResponse);
    }

    @Override
@Transactional(readOnly = true)
public WalletResponse getWalletByPhoneNumber(String phoneNumber) {
    Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Aucun portefeuille trouvé pour le numéro : " + phoneNumber
            ));

    return WalletMapper.toResponse(wallet);
}
    private void validateUniqueness(CreateWalletRequest request) {
        if (walletRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new BusinessException("Un portefeuille existe déjà avec ce numéro de téléphone.");
        }

        if (walletRepository.existsByEmail(request.email())) {
            throw new BusinessException("Un portefeuille existe déjà avec cette adresse email.");
        }

        if (walletRepository.existsByCode(request.code())) {
            throw new BusinessException("Un portefeuille existe déjà avec ce code.");
        }
    }
    @Override
public DepositResponse deposit(Long walletId, DepositRequest request) {
    Wallet wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Aucun portefeuille trouvé avec l'identifiant : " + walletId
            ));

    wallet.setBalance(wallet.getBalance().add(request.amount()));

    Transaction transaction = TransactionFactory.createDeposit(
            wallet,
            request.amount(),
            request.paymentMethod()
    );

    Transaction savedTransaction = transactionRepository.save(transaction);

    return new DepositResponse(
            savedTransaction.getId(),
            wallet.getId(),
            wallet.getPhoneNumber(),
            request.amount(),
            request.paymentMethod().name(),
            wallet.getBalance(),
            wallet.getCurrency(),
            savedTransaction.getCreatedAt()
    );
}

@Override
public WithdrawalResponse withdraw(WithdrawalRequest request) {
    Wallet wallet = walletRepository.findByPhoneNumber(request.phoneNumber())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Aucun portefeuille trouvé pour le numéro : " + request.phoneNumber()
            ));

    BigDecimal fee = withdrawalFeeStrategy.calculateFee(request.amount());
    BigDecimal totalDebited = request.amount().add(fee);

    if (wallet.getBalance().compareTo(totalDebited) < 0) {
        throw new BusinessException(
                "Solde insuffisant. Montant demandé : "
                        + request.amount()
                        + ", frais : "
                        + fee
                        + ", total requis : "
                        + totalDebited
        );
    }

    wallet.setBalance(wallet.getBalance().subtract(totalDebited));

    Transaction transaction = TransactionFactory.createWithdrawal(
            wallet,
            request.amount(),
            fee
    );

    Transaction savedTransaction = transactionRepository.save(transaction);

    return new WithdrawalResponse(
            savedTransaction.getId(),
            wallet.getId(),
            wallet.getPhoneNumber(),
            request.amount(),
            fee,
            totalDebited,
            wallet.getBalance(),
            wallet.getCurrency(),
            savedTransaction.getCreatedAt()
    );
}
}