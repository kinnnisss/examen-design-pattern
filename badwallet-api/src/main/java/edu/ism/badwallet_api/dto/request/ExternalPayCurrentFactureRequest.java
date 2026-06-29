package edu.ism.badwallet_api.dto.request;

import edu.ism.badwallet_api.entity.BillServiceName;
import java.math.BigDecimal;

public record ExternalPayCurrentFactureRequest(
        String walletCode,
        BillServiceName serviceName,
        BigDecimal amount
) {
}