package com.ignitiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.service.CartService;
import com.kibocommerce.sdk.commerce.models.Cart;
import com.kibocommerce.sdk.commerce.models.CartItem;
import com.kibocommerce.sdk.common.ApiException;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService service;
	
	@GetMapping
	public ResponseEntity<?> getOrCreateCart(){
		try {
			Cart cart = service.getOrCreateCart();
			return ResponseEntity.ok(cart);

		} catch (ApiException e) {
			System.out.println("Message" + e.getMessage());
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@GetMapping("?{cartItemId}")
	public ResponseEntity<?> getCartItem(@PathVariable String cartItemId){
		CartItem cartItem;
		try {
			cartItem = service.getCartItem(cartItemId);
			return ResponseEntity.ok(cartItem);

		} catch (ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
 	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<?> deleteItem(String cartItemId){
		try {
			service.deleteCartItem(cartItemId);
			return ResponseEntity.ok().body("Item deleted");
		} catch (ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
 	
 	
 	@PutMapping
 	public ResponseEntity<?> updateCart(Cart cart){
 		try {
 			Cart updatedCart = service.updateCart(cart);
 			return ResponseEntity.ok(updatedCart);
 		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
 		}
 	}
 	
 	@PutMapping("/{cartId}")
 	public ResponseEntity<?> updateCartByCartId(@PathVariable String cartId, Cart cart){
 		try {
			Cart updatedCart = service.updateCartByCartId(cartId, cart);
	 		return ResponseEntity.ok(updatedCart);

		} catch (ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
 	}
	
	
	
}
