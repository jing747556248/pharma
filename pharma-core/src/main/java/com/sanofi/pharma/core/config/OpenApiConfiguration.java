package com.sanofi.pharma.core.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfiguration.
 * @author U1038180
 */
@Configuration
public class OpenApiConfiguration {
	@Bean
	public GroupedOpenApi api() {
	    return GroupedOpenApi.builder()
	            .group("elide")
	            .pathsToMatch("/**")
				.displayName("elide自动生成")
				.packagesToExclude("com.sanofi.pharma.controller")
	            .build();
	}


	@Bean
	public GroupedOpenApi apiV2() {
		return GroupedOpenApi.builder()
				.group("manualModeApi")
				.packagesToScan("com.sanofi.pharma.controller")
				.displayName("手写API（非elide生成）")
				.build();
	}
}
