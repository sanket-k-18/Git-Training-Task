package com.ignitiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.service.ProductService;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductCollection;
import com.kibocommerce.sdk.common.ApiException;

@RestController
@RequestMapping("/products")
public class ProductConrtoller {
	
	@Autowired
	private ProductService client;
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable String productId) {
		try {
			CatalogAdminsProduct product =  client.getProductById(productId);
			return ResponseEntity.ok(product);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}
	
	
	@GetMapping("")
	public ResponseEntity<?> getAllProducts(){
		try {
			CatalogAdminsProductCollection products = client.getAllProducts();
			return ResponseEntity.ok(products);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");

		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addProduct(@RequestBody CatalogAdminsProduct product){
		try {
			CatalogAdminsProduct addedProduct =  client.addProduct(product);
			return ResponseEntity.ok(addedProduct);
		}catch(ApiException e) {
			System.out.println("Error" + e.getCode() + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");

		}
	}
	
	
	@DeleteMapping
	public ResponseEntity<?> deleteProduct(@PathVariable String productId){
		try {
			client.deleteProduct(productId);
			return ResponseEntity.ok().body("Product deleted");
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
		
	}
	
	
}
