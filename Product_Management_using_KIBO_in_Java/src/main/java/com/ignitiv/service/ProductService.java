package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.common.ApiException;
import com.kibocommerce.sdk.catalogadministration.api.ProductsApi;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductCollection;



@Service
public class ProductService {
	
	@Autowired
	private KiboConfig kiboConfig;
	

	public CatalogAdminsProduct getProductById(String productId) throws ApiException{
		ProductsApi api = ProductsApi.builder().withConfig(kiboConfig.getConfiguration()).build();
		CatalogAdminsProduct product = api.getProduct(productId, "BaseProductCode");
		return product;
	}
	
	public CatalogAdminsProductCollection getAllProducts() throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(kiboConfig.getConfiguration()).build();
		return api.getProducts(null, null, null, null, null, null, null, null);
	}

	public CatalogAdminsProduct addProduct(CatalogAdminsProduct product) throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(kiboConfig.getConfiguration()).build();
		CatalogAdminsProduct addedProduct = api.addProduct(product);
		return addedProduct;
	}
	
	public void deleteProduct(String productId) throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(kiboConfig.getConfiguration()).build();
		api.deleteProduct(productId);
	}

}
