package com.jbac.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration>{
	private final Logger log =LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

	public ExampleGatewayFilterFactory() {
		super(Configuration.class);
	}

	@Override
	public GatewayFilter apply(Configuration config) {
		return (exchange,chain)->{
			log.info("Ejecutando pre gateway filter factory: "+config.messaje);
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				Optional.ofNullable(config.cookieValue).ifPresent(cookie->{
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookie).build());
				});
				
				log.info("Ejecutando post gateway filter factory: "+config.messaje);
				
			}));
		};
	}
	
	@Override
	public String name() {
		return "ExampleCookie";
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("message","cookieName","cookieValue");
	}

	public static class Configuration {
		
		private String messaje;
		private String cookieName;
		private String cookieValue;
		
		public String getMessaje() {
			return messaje;
		}
		public void setMessaje(String messaje) {
			this.messaje = messaje;
		}
		public String getCookieValue() {
			return cookieValue;
		}
		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		
	}
}
