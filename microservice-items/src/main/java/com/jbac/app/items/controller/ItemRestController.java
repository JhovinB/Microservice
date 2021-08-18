package com.jbac.app.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbac.app.items.models.Item;
import com.jbac.app.items.service.ItemService;

@RestController
@RequestMapping("/api/v1/items")
public class ItemRestController {
	
	
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item> getListItems(){
		return itemService.findAll();
	}
	
	@GetMapping("/{id}/quantity/{quantity}")
	public Item getItem(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		return itemService.findById(id, quantity);
	}
}
