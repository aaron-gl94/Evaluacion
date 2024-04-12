package com.academia.evaluacion.application.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				description="Proyecto de Evaluación / SpringBoot - Arq. Hexagonal. / Acadamia Indra - Minsait",
				title="Evaluación Arquitectura Hexagonal",
				version="1.0.0"
		)
)

public class ConfigSwaggerUI {}