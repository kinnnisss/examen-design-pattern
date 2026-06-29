package edu.ism.badwallet_api.dto.request;

import edu.ism.badwallet_api.entity.BillServiceName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record PayCurrentBillRequest(

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro doit respecter le format international."
        )
        String phoneNumber,

        @NotNull(message = "Le nom du service est obligatoire.")
        BillServiceName serviceName,

        @NotNull(message = "Le montant est obligatoire.")
        @DecimalMin(
                value = "0.01",
                message = "Le montant doit être supérieur à zéro."
        )
        BigDecimal amount
) {
}