package com.jbac.app.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.jbac.app.products.model.Product;

public interface IProductoRepository extends CrudRepository<Product,Long>{

}
