package com.jbac.app.items.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jbac.app.items.models.Item;
import com.jbac.app.items.models.Product;
import com.jbac.app.items.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
//@RequestMapping("/api/v1/items")
public class ItemRestController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemRestController.class);
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@GetMapping
	public List<Item> getListItems(@RequestParam(name="name",required=false)String name
			,@RequestHeader(name="token-request",required=false)String token){
		System.out.println(name);
		System.out.println(token);
		return itemService.findAll();
	}
	
	//@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/{id}/quantity/{quantity}")
	public Item getItem(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		return cbFactory.create("items")
				.run(()->itemService.findById(id, quantity)
						,e->alternativeMethod(id,quantity,e));
		//return itemService.findById(id, quantity);
	}
	//CircuitBreaker con anotacion
	@CircuitBreaker(name="items",fallbackMethod = "alternativeMethod")
	@GetMapping("/{id}/ver/quantity/{quantity}")
	public Item getItem2(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		return itemService.findById(id, quantity);
	}
	//Timeout  con anotacion
	@CircuitBreaker(name="items",fallbackMethod = "alternativeMethod2")
	@TimeLimiter(name="items")
	@GetMapping("/{id}/ver1/quantity/{quantity}")
	public CompletableFuture<Item> getItem3(@PathVariable("id") Long id,
			@PathVariable("quantity") Integer quantity) {
		return CompletableFuture.supplyAsync(()->itemService.findById(id, quantity));
	}
	
	
	public Item alternativeMethod(Long id,Integer quantity,Throwable e) {
		
		logger.info(e.getMessage());
		
		Item item =new Item();
		Product product = new Product();
		
		
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("smartwatch");
		product.setPrice(1200.50);
		item.setProduct(product);
		return item;
	}
	
	public CompletableFuture<Item> alternativeMethod2(Long id,Integer quantity,Throwable e) {
		
		logger.info(e.getMessage());
		
		Item item =new Item();
		Product product = new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("smartwatch");
		product.setPrice(1200.50);
		item.setProduct(product);
		return CompletableFuture.supplyAsync(()->item);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody Product product) {
		return itemService.save(product);
	}
	
	@PutMapping("/edit/{id}")
	public Product updateProduct(@RequestBody Product product,@PathVariable Long id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		itemService.delete(id);
		return ResponseEntity.ok("Se eliminó correctamente");
	}
}
	
	
	

