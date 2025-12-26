package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.commerce.api.OrderApi;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class OrderService {
	
	@Autowired
	KiboConfig config;
	
	public Order createOrder(String cartId, String quoteId, Order order) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.createOrder(cartId, quoteId, order);
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
}
