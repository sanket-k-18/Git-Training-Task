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
	
	ProductsApi api = ProductsApi.builder().withConfig(kiboConfig.getConfiguration()).build();

	public CatalogAdminsProduct getProductById(String productId) throws ApiException{
		CatalogAdminsProduct product = api.getProduct(productId, "BaseProductCode");
		return product;
	}
	
	public CatalogAdminsProductCollection getAllProducts() throws ApiException {
		return api.getProducts(null, null, null, null, null, null, null, null);
	}
	
	public CatalogAdminsProduct addProduct(CatalogAdminsProduct product) throws ApiException {
		CatalogAdminsProduct addedProduct = api.addProduct(product);
		return addedProduct;
	}
	
	public void deleteProduct(String productId) throws ApiException {
		api.deleteProduct(productId);
	}

}
