package com.myclass.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("com.myclass.controller.admin"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/");
	}
	public ApiInfo apiInfo() {
		return new ApiInfo("My REST API", "Api of Elearning", "API TOS", "Terms of service",
				new Contact("Nguyễn Hoàng Long", "www.nguyenhoanglong.com", "nghoanglong11@yahoo.com"),
				"License of API", "API license URL", Collections.emptyList());
	}
}
