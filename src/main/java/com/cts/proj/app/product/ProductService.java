package com.cts.proj.app.product;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	private static List<String> products;
	
	public ProductService() {
		products = Arrays.asList("Helmet","Cerebro","Wheel Chair","Iron");
	}
	
	public List<String> getAllProducts(){
		return products;
	}
}
