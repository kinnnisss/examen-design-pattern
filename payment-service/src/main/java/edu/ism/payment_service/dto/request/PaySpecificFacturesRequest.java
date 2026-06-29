package main.java.edu.ism.payment_service.dto.request;

import edu.ism.payment_service.entity.ServiceName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PaySpecificFacturesRequest(

        @NotBlank(message = "Le code wallet est obligatoire.")
        String walletCode,

        @NotNull(message = "Le service est obligatoire.")
        ServiceName serviceName,

        @NotEmpty(message = "Au moins une référence de facture est obligatoire.")
        List<String> factureReferences
) {
}