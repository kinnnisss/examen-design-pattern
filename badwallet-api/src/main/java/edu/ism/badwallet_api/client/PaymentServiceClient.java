package edu.ism.badwallet_api.client;

import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import java.time.LocalDate;
import java.util.List;

public interface PaymentServiceClient {

    List<ExternalFactureResponse> getCurrentUnpaidFactures(
            String walletCode,
            String unite
    );

    List<ExternalFactureResponse> getUnpaidFacturesByPeriod(
            String walletCode,
            LocalDate debut,
            LocalDate fin
    );
}