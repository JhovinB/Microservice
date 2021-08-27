package com.jbac.app.products.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbac.app.products.model.Product;
import com.jbac.app.products.service.IProductService;

@RestController
//@RequestMapping("/api/v1/products")
public class ProductRestController {
	
	@Autowired
	private Environment env;
	
	//Para utilizar con RestTemplate
	@Value("${server.port}")
	private Integer port;
	
	
	@Autowired
	IProductService productService;
	
	@GetMapping
	public List<Product> getListProducts(){
		return productService.findAll()
				.stream().map(product->{
					product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					//product.setPort(port);
					return product;
				}).collect(Collectors.toList());
	}
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id) throws InterruptedException {
		
		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		if(id.equals(7L)) {
			//Indica la unidad de tiempo de 5s.
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//product.setPort(port);
		
//		//Lanzar una error
//		boolean ok = false;
//		if(!ok) {
//			throw new RuntimeException("No se pudo cargar el producto");
//		}
//		return product;
		
		//Lanzar una pausa(delay sleep)
		
//		try {
//			Thread.sleep(2000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
		return product;
	}
}
