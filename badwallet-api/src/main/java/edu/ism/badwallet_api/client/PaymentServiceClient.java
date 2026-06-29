package edu.ism.badwallet_api.client;

import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import java.time.LocalDate;
import java.util.List;

import edu.ism.badwallet_api.dto.request.ExternalPayCurrentFactureRequest;
import edu.ism.badwallet_api.dto.request.ExternalPaySpecificFacturesRequest;
import edu.ism.badwallet_api.dto.response.ExternalFacturePaymentResponse;
import edu.ism.badwallet_api.dto.response.ExternalSpecificFacturesPreviewResponse;
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

    ExternalFacturePaymentResponse payCurrentFacture(
        ExternalPayCurrentFactureRequest request
);

ExternalSpecificFacturesPreviewResponse previewSpecificFactures(
        ExternalPaySpecificFacturesRequest request
);

ExternalFacturePaymentResponse paySpecificFactures(
        ExternalPaySpecificFacturesRequest request
);
}