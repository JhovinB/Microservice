package com.jbac.app.items.customers;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jbac.app.items.models.Product;

@FeignClient(name="service-products")
public interface ProductClientRest {
	
	@GetMapping
	public 	List<Product> listProduct();
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id);
}
