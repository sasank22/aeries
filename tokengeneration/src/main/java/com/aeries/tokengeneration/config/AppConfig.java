package com.aeries.tokengeneration.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aeries.tokengeneration.service.TokenValidationFilter;

@Configuration
public class AppConfig {

	@Bean
	public TokenValidationFilter tokenValidationFilter() {
		return new TokenValidationFilter();
	}

	@Bean
	public FilterRegistrationBean<TokenValidationFilter> filterRegistrationBean(
			TokenValidationFilter tokenValidationFilter) {
		FilterRegistrationBean<TokenValidationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(tokenValidationFilter);
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
}
