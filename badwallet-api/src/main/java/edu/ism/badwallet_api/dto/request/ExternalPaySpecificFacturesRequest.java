package edu.ism.badwallet_api.dto.request;

import edu.ism.badwallet_api.entity.BillServiceName;
import java.util.List;

public record ExternalPaySpecificFacturesRequest(
        String walletCode,
        BillServiceName serviceName,
        List<String> factureReferences
) {
}