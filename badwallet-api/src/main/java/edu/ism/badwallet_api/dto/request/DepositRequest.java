package edu.ism.badwallet_api.dto.request;

import edu.ism.badwallet_api.entity.DepositPaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DepositRequest(

        @NotNull(message = "Le montant est obligatoire.")
        @DecimalMin(
                value = "0.01",
                inclusive = true,
                message = "Le montant du dépôt doit être supérieur à zéro."
        )
        BigDecimal amount,

        @NotNull(message = "La méthode de paiement est obligatoire.")
        DepositPaymentMethod paymentMethod
) {
}