package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.dto.EventDTO;
import com.ignitiv.dto.ExtendedPropertyDTO;
import com.ignitiv.entities.OrderEntity;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class EventHandler {
	
	@Autowired
	OrderService orderService;
	
	 
	
	public void handleOrderCreated(EventDTO event) throws ApiException {
		ExtendedPropertyDTO props = event.getExtendedProperties().get(0);
		String orderId = props.getValue();
		
		Order orderCreated =  orderService.getOrderByOderId(orderId);
		OrderEntity order = new OrderEntity();
		order.setKiboOrderId(orderId);
		order.setCustomerAccountId(orderCreated.getCustomerAccountId());
		order.setOrderNumber(orderCreated.getOrderNumber());
		order.setCurrency(orderCreated.getCurrencyCode());
		order.setStatus(orderCreated.getStatus());
		order.setTotalAmount(orderCreated.getDiscountedTotal());
		
		
		System.out.println(order);
	}
}
