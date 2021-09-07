package com.jbac.app.products.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.jbac.app.products.model.Product;
import com.jbac.app.comms.model.Product;
import com.jbac.app.products.repository.IProductoRepository;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	IProductoRepository productRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

}
