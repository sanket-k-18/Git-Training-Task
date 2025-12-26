package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.commerce.api.CartApi;
import com.kibocommerce.sdk.commerce.models.Cart;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class CartService {
	@Autowired
	KiboConfig config;
	
	CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();

	public Cart getOrCreateCart() throws ApiException {
		return api.getOrCreateCart();
	}
	
	public void deleteCartItem(String cartItem) throws ApiException {
		 api.deleteCartItem(cartItem);
	}
	
	public Cart updateCart(Cart cart) throws ApiException {
		Cart updatedCart = api.updateCart(cart);
		return updatedCart;
	}
	
	public Cart updateCartByCartId(String cartId,Cart cart) throws ApiException{
		Cart updatedCart = api.updateCartByCartId(cartId, cart);
		return updatedCart;
	}
	
	
}
