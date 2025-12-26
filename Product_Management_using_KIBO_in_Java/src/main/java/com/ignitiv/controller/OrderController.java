package com.ignitiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.service.OrderService;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.common.ApiException;

@RestController
@RequestMapping("/orders")
public class OrderController {
		
	@Autowired
	OrderService service;
	
	@PostMapping
	public ResponseEntity<?> createOrder(String cartId, String quoteId, Order order){
		try {
			Order createdOrder = service.createOrder(cartId, quoteId, order);
			return ResponseEntity.ok(createdOrder);
		}catch(ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteOrder(String orderId) {
		try {
			service.deleteOrder(orderId);
			return ResponseEntity.ok("Order Deleted");
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> cancelOrder(String orderId){
		try {
			Order order = service.cancleOrder(orderId);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateOrder(String orderId, Order order){
		try {
			Order updatedOrder = service.updateOrder(orderId, order);
			return ResponseEntity.ok(updatedOrder);
		}catch(ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}
}
