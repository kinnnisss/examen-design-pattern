package edu.ism.badwallet_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record TransferRequest(

        @NotBlank(message = "Le numéro de l'expéditeur est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro de l'expéditeur doit respecter le format international."
        )
        String senderPhone,

        @NotBlank(message = "Le numéro du destinataire est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro du destinataire doit respecter le format international."
        )
        String receiverPhone,

        @DecimalMin(
                value = "0.01",
                inclusive = true,
                message = "Le montant du transfert doit être supérieur à zéro."
        )
        BigDecimal amount
) {
}