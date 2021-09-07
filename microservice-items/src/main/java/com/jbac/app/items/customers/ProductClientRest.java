package com.jbac.app.items.customers;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jbac.app.items.models.Product;

@FeignClient(name="service-products")
public interface ProductClientRest {
	
	@GetMapping
	public 	List<Product> listProduct();
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id);
	
	
	@PostMapping
	public Product createProduct(@RequestBody Product product);
	
	@PutMapping("/edit/{id}")
	public Product updateProduct(@RequestBody Product productm,
			@PathVariable Long id);
			
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id);
	
}
