package com.jbac.app.items.service;

import java.util.List;

import com.jbac.app.items.models.Item;

public interface ItemService {
	
	public List<Item> findAll();

	public Item findById(Long id,Integer quantity);
}
