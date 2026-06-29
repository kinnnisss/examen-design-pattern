package edu.ism.badwallet_api.client.impl;

import edu.ism.badwallet_api.client.PaymentServiceClient;
import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import edu.ism.badwallet_api.exception.BusinessException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class PaymentServiceRestClient implements PaymentServiceClient {

    @Qualifier("paymentServiceRestClient")
    private final RestClient paymentServiceRestClient;

@Override
public List<ExternalFactureResponse> getCurrentUnpaidFactures(
        String walletCode,
        String unite
) {
    try {
        ExternalFactureResponse[] responses;

        if (unite == null || unite.isBlank()) {
            responses = paymentServiceRestClient.get()
                    .uri("/api/internal/factures/{walletCode}/current", walletCode)
                    .retrieve()
                    .body(ExternalFactureResponse[].class);
        } else {
            responses = paymentServiceRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/internal/factures/{walletCode}/current")
                            .queryParam("unite", unite)
                            .build(walletCode))
                    .retrieve()
                    .body(ExternalFactureResponse[].class);
        }

        return responses == null ? List.of() : Arrays.asList(responses);

    } catch (RestClientException exception) {
        throw new BusinessException(
                "Le service de paiement est indisponible ou a retourné une erreur."
        );
    }
}
    @Override
    public List<ExternalFactureResponse> getUnpaidFacturesByPeriod(
            String walletCode,
            LocalDate debut,
            LocalDate fin
    ) {
        try {
            ExternalFactureResponse[] responses = paymentServiceRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/internal/factures/{walletCode}/periode")
                            .queryParam("debut", debut)
                            .queryParam("fin", fin)
                            .build(walletCode))
                    .retrieve()
                    .body(ExternalFactureResponse[].class);

            return responses == null ? List.of() : Arrays.asList(responses);

        } catch (RestClientException exception) {
            throw new BusinessException(
                    "Le service de paiement est indisponible ou a retourné une erreur."
            );
        }
    }
}