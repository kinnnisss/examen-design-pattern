package main.java.edu.ism.payment_service.mock;

import edu.ism.payment_service.entity.Facture;
import edu.ism.payment_service.entity.FactureStatus;
import edu.ism.payment_service.entity.ServiceName;
import edu.ism.payment_service.repository.FactureRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockFactureDataInitializer implements CommandLineRunner {

    private final FactureRepository factureRepository;

    @Override
    public void run(String... args) {
        if (factureRepository.count() > 0) {
            return;
        }

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        createFacture(
                "FAC-ISM-3-1",
                "WLT-0000003",
                ServiceName.ISM,
                "ISM",
                new BigDecimal("5000.00"),
                currentMonth
        );

        createFacture(
                "FAC-ISM-3-3",
                "WLT-0000003",
                ServiceName.ISM,
                "ISM",
                new BigDecimal("5000.00"),
                currentMonth.minusMonths(1)
        );

        createFacture(
                "FAC-WOYAFAL-3-1",
                "WLT-0000003",
                ServiceName.WOYAFAL,
                "WOYAFAL",
                new BigDecimal("3000.00"),
                currentMonth
        );

        createFacture(
                "FAC-ISM-1-1",
                "WLT-0000001",
                ServiceName.ISM,
                "ISM",
                new BigDecimal("5000.00"),
                currentMonth
        );
    }

    private void createFacture(
            String reference,
            String walletCode,
            ServiceName serviceName,
            String unite,
            BigDecimal amount,
            LocalDate billingMonth
    ) {
        Facture facture = new Facture();
        facture.setReference(reference);
        facture.setWalletCode(walletCode);
        facture.setServiceName(serviceName);
        facture.setUnite(unite);
        facture.setAmount(amount);
        facture.setStatus(FactureStatus.UNPAID);
        facture.setBillingMonth(billingMonth);
        facture.setDueDate(billingMonth.plusMonths(1).minusDays(1));

        factureRepository.save(facture);
    }
}