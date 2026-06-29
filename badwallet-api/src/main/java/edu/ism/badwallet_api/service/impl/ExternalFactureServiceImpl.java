package edu.ism.badwallet_api.service.impl;

import edu.ism.badwallet_api.client.PaymentServiceClient;
import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import edu.ism.badwallet_api.exception.BusinessException;
import edu.ism.badwallet_api.service.ExternalFactureService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExternalFactureServiceImpl implements ExternalFactureService {

    private final PaymentServiceClient paymentServiceClient;

    @Override
    public List<ExternalFactureResponse> getCurrentUnpaidFactures(
            String walletCode,
            String unite
    ) {
        return paymentServiceClient.getCurrentUnpaidFactures(walletCode, unite);
    }

    @Override
    public List<ExternalFactureResponse> getUnpaidFacturesByPeriod(
            String walletCode,
            LocalDate debut,
            LocalDate fin
    ) {
        if (debut.isAfter(fin)) {
            throw new BusinessException(
                    "La date de début doit être antérieure ou égale à la date de fin."
            );
        }

        return paymentServiceClient.getUnpaidFacturesByPeriod(
                walletCode,
                debut,
                fin
        );
    }
}