package com.jbac.app.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.jbac.app.comms.model"})
public class MicroserviceProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductsApplication.class, args);
	}

}
