package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.commerce.api.CartApi;
import com.kibocommerce.sdk.commerce.models.Cart;
import com.kibocommerce.sdk.commerce.models.CartItem;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class CartService {
	@Autowired
	KiboConfig config;
	
	public Cart getOrCreateCart() throws ApiException {
		CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();
		return api.getOrCreateCart();
	}
	
	public CartItem getCartItem(String cartItemId) throws ApiException {
		CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();
		return api.getCartItem(cartItemId);
	}
	public void deleteCartItem(String cartItem) throws ApiException {
		CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();
		 api.deleteCartItem(cartItem);
	}
	
	public Cart updateCart(Cart cart) throws ApiException {
		CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();
		Cart updatedCart = api.updateCart(cart);
		return updatedCart;
	}
	
	public Cart updateCartByCartId(String cartId,Cart cart) throws ApiException{
		CartApi api = CartApi.builder().withConfig(config.getConfiguration()).build();
		Cart updatedCart = api.updateCartByCartId(cartId, cart);
		return updatedCart;
	}
	
	
}
