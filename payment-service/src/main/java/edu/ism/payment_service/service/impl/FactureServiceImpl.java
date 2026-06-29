package main.java.edu.ism.payment_service.service.impl;

import edu.ism.payment_service.dto.request.PayCurrentFactureRequest;
import edu.ism.payment_service.dto.request.PaySpecificFacturesRequest;
import edu.ism.payment_service.dto.response.FacturePaymentResponse;
import edu.ism.payment_service.dto.response.FactureResponse;
import edu.ism.payment_service.entity.Facture;
import edu.ism.payment_service.entity.FactureStatus;
import edu.ism.payment_service.exception.BusinessException;
import edu.ism.payment_service.exception.ResourceNotFoundException;
import edu.ism.payment_service.repository.FactureRepository;
import edu.ism.payment_service.service.FactureService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getCurrentUnpaidFactures(
            String walletCode,
            String unite
    ) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        List<Facture> factures;

        if (unite == null || unite.isBlank()) {
            factures = factureRepository.findByWalletCodeAndStatusAndBillingMonth(
                    walletCode,
                    FactureStatus.UNPAID,
                    currentMonth
            );
        } else {
            factures = factureRepository.findByWalletCodeAndStatusAndBillingMonthAndUnite(
                    walletCode,
                    FactureStatus.UNPAID,
                    currentMonth,
                    unite.toUpperCase()
            );
        }

        return factures.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getUnpaidFacturesByPeriod(
            String walletCode,
            LocalDate debut,
            LocalDate fin
    ) {
        if (debut.isAfter(fin)) {
            throw new BusinessException(
                    "La date de début doit être antérieure ou égale à la date de fin."
            );
        }

        return factureRepository
                .findByWalletCodeAndStatusAndBillingMonthBetween(
                        walletCode,
                        FactureStatus.UNPAID,
                        debut.withDayOfMonth(1),
                        fin.withDayOfMonth(1)
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public FacturePaymentResponse payCurrentFacture(
            PayCurrentFactureRequest request
    ) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        Facture facture = factureRepository
                .findByWalletCodeAndServiceNameAndStatusAndBillingMonth(
                        request.walletCode(),
                        request.serviceName(),
                        FactureStatus.UNPAID,
                        currentMonth
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune facture impayée du mois en cours pour ce service."
                ));

        if (facture.getAmount().compareTo(request.amount()) != 0) {
            throw new BusinessException(
                    "Le montant envoyé ne correspond pas au montant de la facture : "
                            + facture.getAmount()
            );
        }

        LocalDateTime paidAt = LocalDateTime.now();
        facture.setStatus(FactureStatus.PAID);
        facture.setPaidAt(paidAt);

        return new FacturePaymentResponse(
                request.walletCode(),
                request.serviceName().name(),
                List.of(facture.getReference()),
                facture.getAmount(),
                paidAt
        );
    }

    @Override
    public FacturePaymentResponse paySpecificFactures(
            PaySpecificFacturesRequest request
    ) {
        List<Facture> factures = request.factureReferences()
                .stream()
                .map(reference -> factureRepository
                        .findByReferenceAndWalletCode(reference, request.walletCode())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Facture introuvable : " + reference
                        )))
                .toList();

        for (Facture facture : factures) {
            if (facture.getServiceName() != request.serviceName()) {
                throw new BusinessException(
                        "La facture "
                                + facture.getReference()
                                + " ne correspond pas au service "
                                + request.serviceName()
                );
            }

            if (facture.getStatus() == FactureStatus.PAID) {
                throw new BusinessException(
                        "La facture "
                                + facture.getReference()
                                + " est déjà payée."
                );
            }
        }

        LocalDateTime paidAt = LocalDateTime.now();

        factures.forEach(facture -> {
            facture.setStatus(FactureStatus.PAID);
            facture.setPaidAt(paidAt);
        });

        BigDecimal total = factures.stream()
                .map(Facture::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FacturePaymentResponse(
                request.walletCode(),
                request.serviceName().name(),
                factures.stream().map(Facture::getReference).toList(),
                total,
                paidAt
        );
    }

    private FactureResponse toResponse(Facture facture) {
        return new FactureResponse(
                facture.getReference(),
                facture.getWalletCode(),
                facture.getServiceName().name(),
                facture.getUnite(),
                facture.getAmount(),
                facture.getStatus().name(),
                facture.getBillingMonth(),
                facture.getDueDate(),
                facture.getPaidAt()
        );
    }
}