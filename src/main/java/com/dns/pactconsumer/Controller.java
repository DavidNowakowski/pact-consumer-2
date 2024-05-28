package com.dns.pactconsumer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class Controller {

	private static final String PRODUCTS = "/products";
	private final RestTemplate restTemplate;
	
	
	@GetMapping("/products/:id")
	public ResponseEntity<ProductOut> getOneById(@PathParam("id") Integer id){
		return restTemplate.getForEntity(PRODUCTS + "/" + id, ProductOut.class);
	}

	@GetMapping(PRODUCTS)
	public ResponseEntity<List> getAll(){
		return restTemplate.getForEntity(PRODUCTS, List.class);
	}
	
	@PostMapping(PRODUCTS)
	public ResponseEntity<ProductOut> addProduct(ProductIn product){
		return restTemplate.postForEntity(PRODUCTS, product, ProductOut.class);
	}
	
}
