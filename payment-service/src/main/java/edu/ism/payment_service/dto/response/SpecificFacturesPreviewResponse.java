package main.java.edu.ism.payment_service.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SpecificFacturesPreviewResponse(
        String walletCode,
        String serviceName,
        List<String> factureReferences,
        BigDecimal totalAmount
) {
}