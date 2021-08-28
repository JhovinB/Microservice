package com.jbac.app.items.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbac.app.items.models.Item;
import com.jbac.app.items.models.Product;
import com.jbac.app.items.service.ItemService;

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
}
