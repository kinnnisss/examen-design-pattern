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
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

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
    @GetMapping("/{phoneNumber}")
public ResponseEntity<WalletResponse> getWalletByPhoneNumber(
        @PathVariable String phoneNumber
) {
    return ResponseEntity.ok(
            walletService.getWalletByPhoneNumber(phoneNumber)
    );
}
}