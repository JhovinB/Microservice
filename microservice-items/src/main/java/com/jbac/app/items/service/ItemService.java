package com.jbac.app.items.service;

import java.util.List;

import com.jbac.app.items.models.Item;
//import com.jbac.app.items.models.Product;
import com.jbac.app.comms.model.Product; 

public interface ItemService {
	
	public List<Item> findAll();

	public Item findById(Long id,Integer quantity);

	public Product save(Product product);
	
	public Product update(Product product,Long id);

	public void delete(Long id);
}
