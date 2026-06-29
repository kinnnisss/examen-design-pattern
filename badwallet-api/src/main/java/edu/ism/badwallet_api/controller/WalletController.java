package edu.ism.badwallet_api.controller;

import edu.ism.badwallet_api.dto.request.CreateWalletRequest;
import edu.ism.badwallet_api.dto.response.WalletResponse;
import edu.ism.badwallet_api.service.WalletService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import edu.ism.badwallet_api.dto.request.WithdrawalRequest;
import edu.ism.badwallet_api.dto.response.WithdrawalResponse;
import edu.ism.badwallet_api.dto.request.DepositRequest;
import edu.ism.badwallet_api.dto.response.DepositResponse;
import edu.ism.badwallet_api.dto.response.WalletBalanceResponse;
import edu.ism.badwallet_api.dto.request.TransferRequest;
import edu.ism.badwallet_api.dto.response.TransferResponse;
import edu.ism.badwallet_api.dto.response.TransactionResponse;
import edu.ism.badwallet_api.service.WalletSeedService;
import java.util.List;





@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {


    private final WalletService walletService;
    private final WalletSeedService walletSeedService;


    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(
            @Valid @RequestBody CreateWalletRequest request
    ) {
        WalletResponse response = walletService.createWallet(request);

        return ResponseEntity
                .created(URI.create("/api/wallets/" + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<WalletResponse>> getAllWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("Le numéro de page ne peut pas être négatif.");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("La taille doit être comprise entre 1 et 100.");
        }

        return ResponseEntity.ok(walletService.getAllWallets(page, size));
    }
    @GetMapping("/{phoneNumber}/balance")
public ResponseEntity<WalletBalanceResponse> getWalletBalance(
        @PathVariable String phoneNumber
) {
    return ResponseEntity.ok(
            walletService.getWalletBalance(phoneNumber)
    );
}
@GetMapping("/{phoneNumber}/transactions")
public ResponseEntity<List<TransactionResponse>> getTransactionHistory(
        @PathVariable String phoneNumber
) {
    return ResponseEntity.ok(
            walletService.getTransactionHistory(phoneNumber)
    );
}
    @GetMapping("/{phoneNumber}")
public ResponseEntity<WalletResponse> getWalletByPhoneNumber(
        @PathVariable String phoneNumber
) {
    return ResponseEntity.ok(
            walletService.getWalletByPhoneNumber(phoneNumber)
    );
}

@PostMapping("/{walletId}/deposit")
public ResponseEntity<DepositResponse> deposit(
        @PathVariable Long walletId,
        @Valid @RequestBody DepositRequest request
) {
    return ResponseEntity.ok(walletService.deposit(walletId, request));
}
@PostMapping("/withdraw")
public ResponseEntity<WithdrawalResponse> withdraw(
        @Valid @RequestBody WithdrawalRequest request
) {
    return ResponseEntity.ok(walletService.withdraw(request));
}
@PostMapping("/transfer")
public ResponseEntity<TransferResponse> transfer(
        @Valid @RequestBody TransferRequest request
) {
    return ResponseEntity.ok(walletService.transfer(request));
}
@PostMapping("/seed")
public ResponseEntity<String> seedWallets(
        @RequestParam(defaultValue = "10") int numWallets,
        @RequestParam(defaultValue = "100") int eventsPerWallet
) {
    if (numWallets < 1 || numWallets > 1000) {
        throw new IllegalArgumentException(
                "Le nombre de wallets doit être compris entre 1 et 1000."
        );
    }

    if (eventsPerWallet < 0 || eventsPerWallet > 1000) {
        throw new IllegalArgumentException(
                "Le nombre d'événements par wallet doit être compris entre 0 et 1000."
        );
    }

    walletSeedService.seedWalletsAsync(numWallets, eventsPerWallet);

    String message = "Seeding lancé en arrière-plan : "
            + numWallets
            + " wallets et "
            + eventsPerWallet
            + " événements par wallet.";

    return ResponseEntity.accepted().body(message);
}
}