package com.ignitiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.commerce.api.OrderApi;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.commerce.models.OrderAction;
import com.kibocommerce.sdk.commerce.models.OrderCollection;
import com.kibocommerce.sdk.commerce.models.Payment;
import com.kibocommerce.sdk.commerce.models.PaymentAction;
import com.kibocommerce.sdk.commerce.models.PaymentCollection;
import com.kibocommerce.sdk.common.ApiException;

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
