package edu.ism.badwallet_api.dto.request;

import edu.ism.badwallet_api.entity.BillServiceName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record PaySpecificBillsRequest(

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(
                regexp = "^\\+[1-9][0-9]{7,14}$",
                message = "Le numéro doit respecter le format international."
        )
        String phoneNumber,

        @NotNull(message = "Le nom du service est obligatoire.")
        BillServiceName serviceName,

        @NotEmpty(message = "Au moins une référence de facture est obligatoire.")
        List<String> factureReferences
) {
}