package com.jbac.app.products.service;

import java.util.List;

import com.jbac.app.comms.model.Product;

//import com.jbac.app.products.model.Product;

public interface IProductService {
	
	public List<Product> findAll();

	public Product findById(Long id);
	
	public Product save(Product product);
	
	public void deleteById(Long id);
}
