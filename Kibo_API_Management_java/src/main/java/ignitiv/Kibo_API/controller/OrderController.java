package ignitiv.Kibo_API.controller;


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

import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.commerce.models.OrderAction;
import com.kibocommerce.sdk.commerce.models.OrderCollection;
import com.kibocommerce.sdk.common.ApiException;

import ignitiv.Kibo_API.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService service;
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestParam (required = false) String cartItd, @RequestParam(required = false) String quoteId, @RequestBody Order order){
		try {
			Order createdOrder = service.createOrder(cartItd, quoteId, order);
			return ResponseEntity.ok(createdOrder);
		} catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/action/{orderId}")
	public ResponseEntity<?> performOrderAction(@PathVariable String orderId, @RequestBody OrderAction action){
		try {
			Order order = service.performOrderAction(orderId, action);
			return ResponseEntity.ok(order);
		} catch(ApiException e) {
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
	
	@DeleteMapping("/{orderiD}")
	public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
		try {
			Order order = service.cancleOrder(orderId);
			return ResponseEntity.ok(order);
			} catch(ApiException e) {
				return ResponseEntity.status(e.getCode()).body(e.getMessage());
			}
	}
	
	@PutMapping(value = "/update/{tenantId}/{orderId}", consumes = "application/json")
	public ResponseEntity<?> updateOrder(@PathVariable String tenantId,@PathVariable String orderId, @RequestBody Order order){
		try {
			Order updatedOrder = service.updateOrder(tenantId,orderId, order);
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
			Order order = service.getOrderByOrderId(orderId);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

}
