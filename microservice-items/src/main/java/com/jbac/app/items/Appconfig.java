package com.jbac.app.items;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Appconfig {
	
	@Bean("clientRest")
	public RestTemplate registerRestTemplate() {
		return new RestTemplate();
	}
}
