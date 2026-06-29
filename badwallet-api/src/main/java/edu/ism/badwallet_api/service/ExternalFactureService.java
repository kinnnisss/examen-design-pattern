package edu.ism.badwallet_api.service;

import edu.ism.badwallet_api.dto.response.ExternalFactureResponse;
import java.time.LocalDate;
import java.util.List;

public interface ExternalFactureService {

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