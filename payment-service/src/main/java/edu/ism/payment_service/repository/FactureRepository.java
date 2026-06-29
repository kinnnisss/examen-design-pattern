package main.java.edu.ism.payment_service.repository;

import edu.ism.payment_service.entity.Facture;
import edu.ism.payment_service.entity.FactureStatus;
import edu.ism.payment_service.entity.ServiceName;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByWalletCodeAndStatusAndBillingMonth(
            String walletCode,
            FactureStatus status,
            LocalDate billingMonth
    );

    List<Facture> findByWalletCodeAndStatusAndBillingMonthAndUnite(
            String walletCode,
            FactureStatus status,
            LocalDate billingMonth,
            String unite
    );

    List<Facture> findByWalletCodeAndStatusAndBillingMonthBetween(
            String walletCode,
            FactureStatus status,
            LocalDate debut,
            LocalDate fin
    );

    Optional<Facture> findByReferenceAndWalletCode(String reference, String walletCode);

    Optional<Facture> findByWalletCodeAndServiceNameAndStatusAndBillingMonth(
            String walletCode,
            ServiceName serviceName,
            FactureStatus status,
            LocalDate billingMonth
    );
}