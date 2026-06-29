package edu.ism.badwallet_api.controller;

import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import edu.ism.badwallet_api.service.ExternalFactureService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/factures")
@RequiredArgsConstructor
public class ExternalFactureController {

    private final ExternalFactureService externalFactureService;

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<List<ExternalFactureResponse>> getCurrentUnpaidFactures(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite
    ) {
        return ResponseEntity.ok(
                externalFactureService.getCurrentUnpaidFactures(
                        walletCode,
                        unite
                )
        );
    }

    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<List<ExternalFactureResponse>> getUnpaidFacturesByPeriod(
            @PathVariable String walletCode,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate debut,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fin
    ) {
        return ResponseEntity.ok(
                externalFactureService.getUnpaidFacturesByPeriod(
                        walletCode,
                        debut,
                        fin
                )
        );
    }
}