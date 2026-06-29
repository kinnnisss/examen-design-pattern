package edu.ism.payment_service.service;

import edu.ism.payment_service.dto.request.PayCurrentFactureRequest;
import edu.ism.payment_service.dto.request.PaySpecificFacturesRequest;
import edu.ism.payment_service.dto.response.FacturePaymentResponse;
import edu.ism.payment_service.dto.response.FactureResponse;
import java.time.LocalDate;
import java.util.List;

public interface FactureService {

    List<FactureResponse> getCurrentUnpaidFactures(
            String walletCode,
            String unite);

    List<FactureResponse> getUnpaidFacturesByPeriod(
            String walletCode,
            LocalDate debut,
            LocalDate fin);

    FacturePaymentResponse payCurrentFacture(PayCurrentFactureRequest request);

    FacturePaymentResponse paySpecificFactures(PaySpecificFacturesRequest request);
}