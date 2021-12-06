package io.oferto.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.oferto.business.domain.Product;

@RestController
@RequestMapping("api/products")
public class ProductController {
	Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private List<Product> products = new ArrayList<Product>(
			Arrays.asList(new Product("001", "Apple", 5, true),
						  new Product("002", "Banana", 4.3f, false),
						  new Product("003", "Orange", 3.2f, true)));
	
	@PreAuthorize("hasAnyRole('admin','operator', 'user')")
	@RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> getProducts() throws Exception {
        log.info("Executing getProducts");
                    	
    	return products;
    }
	
	@PreAuthorize("hasAnyRole('admin','operator', 'user')")
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("code") String code) throws Exception {
        log.info("Executing getProduct");
              
        Product product = products.stream()
        	.filter(entity -> code.equals(entity.getCode()))
        	.findAny()
        	.orElse(null);
        
    	return product;
    }
	
	@PreAuthorize("hasAnyRole('admin','operator')")
	@RequestMapping(value = "", method = RequestMethod.POST)
    public List<Product> createProduct(@RequestBody Product product) throws Exception {
        log.info("Executing createProduct");
              
        products.add(product);
        
    	return products;
    }
	
	@PreAuthorize("hasAnyRole('admin','operator')")
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
    public boolean removeProduct(@PathVariable String code) throws Exception {
        log.info("Executing removeProduct");
              
        Product product = products.stream()
            	.filter(entity -> code.equals(entity.getCode()))
            	.findAny()
            	.orElse(null);
        
        if (product != null)
        	return products.remove(product);
        
    	return false;
    }
}