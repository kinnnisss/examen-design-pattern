package main.java.edu.ism.payment_service.controller;

import edu.ism.payment_service.dto.request.PayCurrentFactureRequest;
import edu.ism.payment_service.dto.request.PaySpecificFacturesRequest;
import edu.ism.payment_service.dto.response.FacturePaymentResponse;
import edu.ism.payment_service.dto.response.FactureResponse;
import edu.ism.payment_service.service.FactureService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.java.edu.ism.payment_service.dto.response.SpecificFacturesPreviewResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.ism.payment_service.dto.response.SpecificFacturesPreviewResponse;

@RestController
@RequestMapping("/api/internal/factures")
@RequiredArgsConstructor
public class FactureInternalController {

    private final FactureService factureService;

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<List<FactureResponse>> getCurrentUnpaidFactures(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite
    ) {
        return ResponseEntity.ok(
                factureService.getCurrentUnpaidFactures(walletCode, unite)
        );
    }

    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<List<FactureResponse>> getUnpaidFacturesByPeriod(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fin
    ) {
        return ResponseEntity.ok(
                factureService.getUnpaidFacturesByPeriod(walletCode, debut, fin)
        );
    }

    @PostMapping("/pay-current")
    public ResponseEntity<FacturePaymentResponse> payCurrentFacture(
            @Valid @RequestBody PayCurrentFactureRequest request
    ) {
        return ResponseEntity.ok(
                factureService.payCurrentFacture(request)
        );
    }

    @PostMapping("/pay-specific")
    public ResponseEntity<FacturePaymentResponse> paySpecificFactures(
            @Valid @RequestBody PaySpecificFacturesRequest request
    ) {
        return ResponseEntity.ok(
                factureService.paySpecificFactures(request)
        );
    }
    @PostMapping("/preview-specific")
public ResponseEntity<SpecificFacturesPreviewResponse> previewSpecificFactures(
        @Valid @RequestBody PaySpecificFacturesRequest request
) {
    return ResponseEntity.ok(
            factureService.previewSpecificFactures(request)
    );
}
}