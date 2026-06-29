package main.java.edu.ism.payment_service.dto.request;

import edu.ism.payment_service.entity.ServiceName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PayCurrentFactureRequest(

        @NotBlank(message = "Le code wallet est obligatoire.")
        String walletCode,

        @NotNull(message = "Le service est obligatoire.")
        ServiceName serviceName,

        @NotNull(message = "Le montant est obligatoire.")
        @DecimalMin(
                value = "0.01",
                message = "Le montant doit être supérieur à zéro."
        )
        BigDecimal amount
) {
}