package com.ignitiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.service.OrderService;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.commerce.models.OrderAction;
import com.kibocommerce.sdk.commerce.models.OrderCollection;
import com.kibocommerce.sdk.commerce.models.Payment;
import com.kibocommerce.sdk.commerce.models.PaymentAction;
import com.kibocommerce.sdk.commerce.models.PaymentCollection;
import com.kibocommerce.sdk.common.ApiException;

@RestController
@RequestMapping("/orders")
public class OrderController {
		
	@Autowired
	OrderService service;
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestParam (required = false) String cartId, @RequestParam(required = false) String quoteId, @RequestBody Order order){
		try {
			Order createdOrder = service.createOrder(cartId, quoteId, order);
			return ResponseEntity.ok(createdOrder);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/action/{orderId}")
	public ResponseEntity<?> performOrderAction(@PathVariable String orderId, @RequestBody OrderAction action){
		try {
			Order order = service.performOrderAction(orderId, action);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());	
		}
	}
	
	@DeleteMapping("/delete/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable String orderId) {
		try {
			service.deleteOrder(orderId);
			return ResponseEntity.ok("Order Deleted");
		} catch (ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable String orderId){
		try {
			Order order = service.cancleOrder(orderId);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PutMapping("/orderId")
	public ResponseEntity<?> updateOrder(@PathVariable String orderId, @RequestBody Order order){
		try {
			Order updatedOrder = service.updateOrder(orderId, order);
			return ResponseEntity.ok(updatedOrder);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	 
	@GetMapping
	public ResponseEntity<?>getOrders(){
		try {
			OrderCollection orders = service.getOrders();
			return ResponseEntity.ok(orders);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderByOrderId(@PathVariable String orderId) {
		try {
			Order order = service.getOrderByOderId(orderId);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/payment/{orderId}")
	public ResponseEntity<?>createPayment(@PathVariable String orderId, @RequestBody PaymentAction payment){
		try {
			Order order = service.createPayment(orderId, payment);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			System.out.println(e);
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/payment/{orderId}/action/{paymentId}")
	public ResponseEntity<?> performPaymentAction(@PathVariable String orderId, @PathVariable String paymentId, @RequestBody PaymentAction action){
		try {
			Order order = service.performPaymentAction(orderId, paymentId, action);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@GetMapping("/payment")
	public ResponseEntity<?> getPayment(@RequestParam String orderId, @RequestParam(required = false) String paymentId){
		try {
		if(paymentId == null) {
			PaymentCollection payments = service.getPayments(orderId);
			return ResponseEntity.ok(payments);
		}
		Payment payment = service.getPayment(orderId, paymentId);
		return ResponseEntity.ok(payment);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@GetMapping("/payment/action")
	public ResponseEntity<?> getAvailablePaymentActions(@RequestParam String orderId, @RequestParam String paymentId){
		try {
			List<String> actions = service.getAvailablePaymentActions(orderId, paymentId);
			return ResponseEntity.ok(actions);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
