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
import com.kibocommerce.sdk.commerce.models.AutoRefundRequest;
import com.kibocommerce.sdk.commerce.models.FulfillmentAction;
import com.kibocommerce.sdk.commerce.models.ModelReturn;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.commerce.models.OrderAction;
import com.kibocommerce.sdk.commerce.models.OrderCollection;
import com.kibocommerce.sdk.commerce.models.OrderReturnableItemCollection;
import com.kibocommerce.sdk.commerce.models.Payment;
import com.kibocommerce.sdk.commerce.models.PaymentAction;
import com.kibocommerce.sdk.commerce.models.PaymentCollection;
import com.kibocommerce.sdk.commerce.models.RestockableReturnItem;
import com.kibocommerce.sdk.commerce.models.ReturnAction;
import com.kibocommerce.sdk.commerce.models.ReturnCollection;
import com.kibocommerce.sdk.commerce.models.ReturnItem;
import com.kibocommerce.sdk.commerce.models.ReturnItemCollection;
import com.kibocommerce.sdk.commerce.models.ReturnItemSpecifier;
import com.kibocommerce.sdk.common.ApiException;
import com.kibocommerce.sdk.fulfillment.models.EntityModelOfShipment;
import com.kibocommerce.sdk.fulfillment.models.TaskComplete;

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
			return ResponseEntity.ok("OrderEntity Deleted");
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
	
	
	@PostMapping("/fulfill/{orderId}")
	public ResponseEntity<?> performFulfillmentAction(@PathVariable String orderId, @RequestBody FulfillmentAction action){
		try {
		Order  order = service.performFulfillment(orderId, action);
		return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PutMapping("/fulfill/{shipmentNo}")
	public ResponseEntity<?> fulfillShipment(@PathVariable Integer shipmentNo, @RequestParam (required = false) String ifMatch ){
		try {
			EntityModelOfShipment shipment =  service.fulfillShipment(shipmentNo, ifMatch);
			return ResponseEntity.ok(shipment);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	
	@PostMapping("/return")
	public ResponseEntity<?> createReturn(@RequestBody ModelReturn returns){
		try {
			ModelReturn returnCreated = service.createReturn(returns);
			return ResponseEntity.ok(returnCreated);
		} catch (ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@GetMapping("/return/{returnId}")
	public ResponseEntity<?> getReturn(@PathVariable String returnId){
		try {
			ModelReturn returns = service.getReturn(returnId);
			return ResponseEntity.ok(returns);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@GetMapping("/returnable/items/{orderId}")
	public ResponseEntity<?> getReturnableItems(@PathVariable String orderId){
		try {
			OrderReturnableItemCollection items = service.getReturnableItem(orderId);
			return ResponseEntity.ok(items);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/return/action")
	public ResponseEntity<?> performReturnAction(@RequestBody ReturnAction action){
		try {
			ReturnCollection returnAction = service.performReturnAction(action);
			return ResponseEntity.ok(returnAction);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@PutMapping("/return/update/{returnId}")
	public ResponseEntity<?> updateReturn(@PathVariable String returnId, @RequestBody ModelReturn returnn){
		try {
			ModelReturn updatedReturn = service.updateReturn(returnId, returnn);
			return ResponseEntity.ok(updatedReturn);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	
	@GetMapping("/return/items/{returnId}")
	public ResponseEntity<?> getReturnItems(@PathVariable String returnId){
		try {
			ReturnItemCollection items =  service.getReturnItems(returnId);
			return ResponseEntity.ok(items);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/return/item/{returnId}")
	public ResponseEntity<?> createReturnItem(@PathVariable String returnId, @RequestBody ReturnItem item){
		try {
			ModelReturn returns = service.createReturnItem(returnId, item);
			return ResponseEntity.ok(returns);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@GetMapping("/shipment/{shipmentNumber}")
	public ResponseEntity<?> getShipment(@PathVariable Integer shipmentNumber){
		try {
			EntityModelOfShipment shipment = service.getShipment(shipmentNumber);
			return ResponseEntity.ok(shipment);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
		
	@PostMapping("/return/restock/{returnId}")
	public ResponseEntity<?> restockItem(@PathVariable String returnId, @RequestBody List<RestockableReturnItem> item){
		try {
			ModelReturn items = service.restockReturn(returnId, item);
			return ResponseEntity.ok(items);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());        
	}
	}
	
	
	@PostMapping("/return/refund/{returnId}")
	public ResponseEntity<?> autoRefund(@PathVariable String returnId, @RequestBody List<AutoRefundRequest> refund){
		try {
			ModelReturn refunds = service.autoRefund(returnId, refund);
			return ResponseEntity.ok(refunds);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@PostMapping("/return/payment/{returnId}")
	public ResponseEntity<?>createPaymentActionForReturn(@PathVariable String returnId, @RequestBody PaymentAction action){
		try {
			ModelReturn payment =  service.createPaymentActionForReturn(returnId, action);
			return ResponseEntity.ok(payment);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@PutMapping("/shipment/{shipmentNumber}/task/{task}")
	public ResponseEntity<?> executeShipmentTask(@PathVariable Integer shipmentNumber, @PathVariable String task, @RequestBody TaskComplete taskComplete){
		try {
			EntityModelOfShipment shipment =  service.executeShipmentTask(shipmentNumber, task, taskComplete);
			return ResponseEntity.ok(shipment);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/return/order/{returnId}")
	public ResponseEntity<?> createReturnOrder(@PathVariable String returnId, @RequestBody (required = false) List<ReturnItemSpecifier> item){
		try {
			Order order = service.createReturnOrder(returnId, item);
			return ResponseEntity.ok(order);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
