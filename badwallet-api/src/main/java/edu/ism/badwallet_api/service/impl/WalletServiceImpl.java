package edu.ism.badwallet_api.service.impl;

import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import edu.ism.badwallet_api.entity.Wallet;
import edu.ism.badwallet_api.exception.BusinessException;
import edu.ism.badwallet_api.mapper.WalletMapper;
import edu.ism.badwallet_api.repository.WalletRepository;
import edu.ism.badwallet_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletResponse createWallet(CreateWalletRequest request) {
        validateUniqueness(request);

        Wallet wallet = WalletMapper.toEntity(request);
        Wallet savedWallet = walletRepository.save(wallet);

        return WalletMapper.toResponse(savedWallet);
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
}