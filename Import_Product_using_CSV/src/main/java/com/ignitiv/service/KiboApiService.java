package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.catalogadministration.api.ProductsApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductAttributesApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductOptionsApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductTypesApi;
import com.kibocommerce.sdk.catalogadministration.api.ProductVariationsApi;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductOption;
import com.kibocommerce.sdk.catalogadministration.models.ProductInCatalogInfo;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductTypeCollection;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationCollection;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationPagedCollection;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class KiboApiService {

	@Autowired
	private KiboConfig config;

	public CatalogAdminsProduct getProduct(String productCode) throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(config.getConfiguration()).build();
		return api.getProduct(productCode, "BaseProductCode");
	}

	public CatalogAdminsProduct addProduct(CatalogAdminsProduct product) throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(config.getConfiguration()).build();
		CatalogAdminsProduct addedProduct = api.addProduct(product);
		return addedProduct;
	}

	public CatalogAdminsAttribute getAttribute(String attrFQN) throws ApiException {
		ProductAttributesApi api = ProductAttributesApi.builder().withConfig(config.getConfiguration()).build();
		return api.getAttribute(attrFQN, "baseAttributeCode");
	}

	public ProductTypeCollection getProductTypes(Integer startIndex, Integer pageSize) throws ApiException {
		ProductTypesApi api = ProductTypesApi.builder().withConfig(config.getConfiguration()).build();
		return api.getProductTypes(startIndex, pageSize, null, null, null);
	}

	public CatalogAdminsProductOption createOption(String productCode, CatalogAdminsProductOption option)
			throws ApiException {
		ProductOptionsApi api = ProductOptionsApi.builder().withConfig(config.getConfiguration()).build();
		CatalogAdminsProductOption response = api.addOption(productCode, option);
		System.out.println("response from service " + response);
		return response;

	}

	public CatalogAdminsAttribute createAttribute(CatalogAdminsAttribute attribute) throws ApiException {
		ProductAttributesApi api = ProductAttributesApi.builder().withConfig(config.getConfiguration()).build();
		return api.addAttribute(attribute);
	}
	
	public ProductType createProductType(ProductType productType) throws ApiException {
		ProductTypesApi api = ProductTypesApi.builder().withConfig(config.getConfiguration()).build();
		return api.addProductType(productType);
	}

	public AttributeInProductType addAttrToProductType(Integer productTypeId, AttributeInProductType attribute, String attrType) throws ApiException {
		ProductTypesApi api = ProductTypesApi.builder().withConfig(config.getConfiguration()).build();
		
		System.out.println("-------------------------------------Inside Add attribute to product type base Api--------------------------------------------");
		System.out.println("Product Type :" + attrType);
		if (attrType.equalsIgnoreCase("extra")) {
			return api.addExtra(productTypeId, attribute);
		}
		if (attrType.equalsIgnoreCase("property")) {
			return api.addProperty(productTypeId, attribute);
		}

		return api.addOption(productTypeId, attribute);
	}
	
	public ProductInCatalogInfo addProductInCatalog(String productCode, ProductInCatalogInfo productInfo) throws ApiException {
		ProductsApi api = ProductsApi.builder().withConfig(config.getConfiguration()).build();
		return api.addProductInCatalog(productCode, productInfo);
	}

	
	
	public ProductVariationPagedCollection getProductVariations(String productCode) throws ApiException {
		ProductVariationsApi api = ProductVariationsApi.builder().withConfig(config.getConfiguration()).build();
	    return api.getProductVariations(productCode, null, null, null, null);
	}
	
public ProductVariation updateVariation( String productCode, String variationKey, ProductVariation productVariation) throws ApiException {
		
		ProductVariationsApi api = ProductVariationsApi.builder().withConfig(config.getConfiguration()).build();
		return api.updateProductVariation(productCode, variationKey, productVariation );
	}
}
