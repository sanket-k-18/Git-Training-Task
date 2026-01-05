package ignitiv.Kibo_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kibocommerce.sdk.commerce.api.OrderApi;
import com.kibocommerce.sdk.commerce.models.Order;
import com.kibocommerce.sdk.commerce.models.OrderAction;
import com.kibocommerce.sdk.commerce.models.OrderCollection;
import com.kibocommerce.sdk.common.ApiException;

import ignitiv.Kibo_API.config.KiboConfig;

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
	
	public Order updateOrder(String tenantId, String orderId, Order order) throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.updateOrder(orderId, "ApplyAndCommit", null, order);
	}
	
	public OrderCollection getOrders() throws ApiException {
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrders(null, null, null, null, null, null, null, null, null);
	}
	
	public Order getOrderByOrderId(String orderId) throws ApiException{
		OrderApi api = OrderApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrder(orderId, null, null, null);
		
	}
}
