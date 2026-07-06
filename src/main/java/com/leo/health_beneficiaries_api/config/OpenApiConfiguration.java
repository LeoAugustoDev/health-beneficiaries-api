package com.leo.health_beneficiaries_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public OpenAPI healthPlanBeneficiariesOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Health Plan Beneficiaries API")
						.description("API para gerenciamento de beneficiarios de plano de saude e documentos associados.")
						.version("v1"));
	}
}
