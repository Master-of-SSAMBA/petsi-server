package com.ssamba.petsi.user_service.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components().addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
			.info(apiInfo())
			.addSecurityItem(new SecurityRequirement().addList("bearer-key"));
	}

	private Info apiInfo() {
		return new Info()
			.title("Petsi")
			.description("<h3>Petsi에서 사용되는 RESTAPI에 대한 문서를 제공한다.</h3>")
			.version("1.0.0");
	}
}
