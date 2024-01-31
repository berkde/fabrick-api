package com.service.fabrickapi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fabrick")
public record Credentials(String baseURL, String authSchema, String apiKey,
                          String balanceURL, String transactionsURL, String transfersURL) {
}
