package edu.ism.badwallet_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient paymentServiceRestClient(
            @Value("${payment-service.base-url}") String paymentServiceBaseUrl
    ) {
        return RestClient.builder()
                .baseUrl(paymentServiceBaseUrl)
                .build();
    }
}