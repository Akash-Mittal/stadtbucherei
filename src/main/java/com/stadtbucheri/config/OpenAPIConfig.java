package com.stadtbucheri.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI(@Autowired(required = false) BuildProperties buildProperties) {
		return new OpenAPI().addServersItem(new Server().url("/"))
				.info(new Info().title("Stadtbucherei By Team 58Agents")
						.version((buildProperties != null) ? buildProperties.getVersion() : "SNAPSHOT").description("")
						.license(new License().name("License: MIT").url("https://opensource.org/licenses/MIT")));
	}

}
