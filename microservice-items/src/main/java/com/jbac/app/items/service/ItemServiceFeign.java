package com.jbac.app.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jbac.app.items.customers.ProductClientRest;
import com.jbac.app.items.models.Item;
import com.jbac.app.items.models.Product;

@Service("serviceFeign")
//@Primary
public class ItemServiceFeign implements ItemService{
	
	@Autowired
	private ProductClientRest clientFeign;
	
	@Override
	public List<Item> findAll() {
		return clientFeign.listProduct()
				.stream()
				.map(p->new Item(p,1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(clientFeign.getProduct(id),quantity);
	}

	@Override
	public Product save(Product product) {
		
		return clientFeign.createProduct(product);
	}

	@Override
	public Product update(Product product, Long id) {
		return clientFeign.updateProduct(product, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.deleteProduct(id);
	}

}
