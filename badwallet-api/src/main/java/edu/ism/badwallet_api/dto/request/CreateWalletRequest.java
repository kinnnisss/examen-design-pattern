package edu.ism.badwallet_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateWalletRequest(

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro doit respecter le format international, par exemple +221779998877."
        )
        String phoneNumber,

        @NotBlank(message = "L'adresse email est obligatoire.")
        @Email(message = "L'adresse email est invalide.")
        String email,

        @DecimalMin(
                value = "0.00",
                inclusive = true,
                message = "Le solde initial ne peut pas être négatif."
        )
        BigDecimal initialBalance,

        @NotBlank(message = "Le code wallet est obligatoire.")
        @Size(max = 50, message = "Le code wallet ne doit pas dépasser 50 caractères.")
        String code,

        @NotBlank(message = "La devise est obligatoire.")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "La devise doit être composée de 3 lettres majuscules, par exemple XOF."
        )
        String currency
) {
}