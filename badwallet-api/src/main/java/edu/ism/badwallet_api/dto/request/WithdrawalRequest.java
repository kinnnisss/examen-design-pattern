package edu.ism.badwallet_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record WithdrawalRequest(

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro doit respecter le format international, par exemple +221779998877."
        )
        String phoneNumber,

        @NotNull(message = "Le montant est obligatoire.")
        @DecimalMin(
                value = "0.01",
                inclusive = true,
                message = "Le montant du retrait doit être supérieur à zéro."
        )
        BigDecimal amount
) {
}