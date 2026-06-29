package edu.ism.badwallet_api.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ExternalSpecificFacturesPreviewResponse(
        String walletCode,
        String serviceName,
        List<String> factureReferences,
        BigDecimal totalAmount
) {
}