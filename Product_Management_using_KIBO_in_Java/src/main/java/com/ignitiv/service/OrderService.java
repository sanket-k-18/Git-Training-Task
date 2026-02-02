package com.ignitiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.commerce.api.OrderApi;
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
import com.kibocommerce.sdk.commerce.api.ReturnApi;
import com.kibocommerce.sdk.common.ApiException;
import com.kibocommerce.sdk.fulfillment.api.ShipmentApi;
import com.kibocommerce.sdk.fulfillment.models.EntityModelOfShipment;
import com.kibocommerce.sdk.fulfillment.models.TaskComplete;

@Service
public class OrderService {
	
	@Autowired
	KiboConfig config;
	
	public Order createOrder(String cartId, String quoteId, Order order) throws ApiException {       
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.createOrder(cartId, quoteId, order);
	}
	
	public Order performOrderAction(String orderId, OrderAction action) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.performOrderAction(orderId, action);
	}
	
	public void deleteOrder(String orderId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		  api.deleteOrderDraft(orderId, null);
	}
	
	public Order cancleOrder(String orderId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		Order order = api.cancelOrder(orderId, null);
		return order;			
	}
	
	public Order updateOrder(String orderId, Order order) throws ApiException{
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.updateOrder(orderId, null, null, order);
	}
	
	public OrderCollection getOrders() throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrders(null, null, null, null, null, null, null, null, null);
	}
	
	public Order getOrderByOderId(String orderId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrder(orderId, null, null, null);
	}
	
//	public void addItemToOrder(String orderId) throws ApiException{
//		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
//		api.createOrderItem(orderId, orderId, orderId, null, orderId, null);
//	}
	
	public Order createPayment(String orderId, PaymentAction payment) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.createPaymentAction(orderId, payment);
	}
	
	public Order performPaymentAction(String orderId, String paymentId, PaymentAction action) throws ApiException{
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.performPaymentAction(orderId, paymentId, action);
	}
	
	
	public Payment getPayment(String orderId, String paymentId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getPayment(orderId, paymentId);
	}
	
	
	public PaymentCollection getPayments(String orderId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getPayments(orderId);
	}
	
	public List<String> getAvailablePaymentActions(String orderId, String paymentId) throws ApiException{
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getAvailablePaymentActions(orderId, paymentId);
	}
	
	public  Order performFulfillment(String orderId, FulfillmentAction action) throws ApiException{
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.performFulfillmentAction(orderId, action);
	}
	
	public EntityModelOfShipment fulfillShipment(Integer shipmentNo, String ifMatch) throws ApiException {
		ShipmentApi api = ShipmentApi.builder().withConfig(config.getConfiguration()).build();
		return api.fulfillShipmentUsingPUT(shipmentNo, ifMatch);
	}
	
	
	
	public ModelReturn createReturn(ModelReturn returns) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.createReturn(returns);
	}
	
	public ModelReturn getReturn (String returnId) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.getReturn(returnId);
	}
	
	public OrderReturnableItemCollection getReturnableItem(String orderId) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrderReturnableItems(orderId);
	}
	
	
	public ReturnCollection performReturnAction(ReturnAction action) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.performReturnActions(action);
	}
	
	
	public ModelReturn updateReturn (String returnId, ModelReturn returnn) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.updateReturn(returnId, returnn);
	}
	
	
	public ReturnItemCollection getReturnItems(String returnId) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.getReturnItems(returnId);
	}
	
	public ModelReturn createReturnItem (String returnId, ReturnItem item) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.createReturnItem(returnId, item);
	}
	
	public EntityModelOfShipment getShipment(Integer shipmentNumber) throws ApiException {
		ShipmentApi api = ShipmentApi.builder().withConfig(config.getConfiguration()).build();
		return api.getShipmentUsingGET(shipmentNumber);
	}
	
	public ModelReturn restockReturn(String returnId, List<RestockableReturnItem> item) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.restockReturnItems(returnId, item);
	}
	
	public ModelReturn autoRefund(String returnId, List<AutoRefundRequest> refund) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.autoRefund(returnId, refund);
	}
		
	
	public ModelReturn createPaymentActionForReturn(String returnId, PaymentAction action) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.createPaymentActionForReturn(returnId, action);
	}
	
	public EntityModelOfShipment executeShipmentTask(Integer shipmentNumber, String task, TaskComplete taskComplete) throws ApiException {
		ShipmentApi api = ShipmentApi.builder().withConfig(config.getConfiguration()).build();
		return api.executeUsingPUT(shipmentNumber, task, null, taskComplete);
	}
	
	public Order createReturnOrder(String returnId, List<ReturnItemSpecifier> returnItemSpecifier) throws ApiException {
		ReturnApi api = ReturnApi.builder().withConfig(config.getConfiguration()).build();
		return api.createReturnShippingOrder(returnId, returnItemSpecifier);
	}
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
