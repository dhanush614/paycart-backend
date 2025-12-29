package com.paycart.wallet.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "PayCart Wallet Service API", version = "1.0", description = "Wallet operations: create wallet, top-up, debit, transactions"))
public class OpenApiConfig {
}