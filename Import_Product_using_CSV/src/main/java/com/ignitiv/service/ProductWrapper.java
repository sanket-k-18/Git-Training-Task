package com.ignitiv.service;

import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;

public class ProductWrapper {

	private CatalogAdminsProduct product;
	private ProductVariation variation;
	private String parentProductCode;
	private String variationKey;
	private Integer catalogId;
	private String operation;
	private Boolean isActive;

	public ProductWrapper(CatalogAdminsProduct product, Integer catalogId, String operation, Boolean isActive) {
		this.product = product;
		this.catalogId = catalogId;
		this.operation = operation;
		this.isActive = isActive;
	}

	public ProductWrapper(CatalogAdminsProduct product, Integer catalogId, Boolean isActive) {
		this(product, catalogId, "Create", isActive);
	}

	

	public ProductWrapper(String productCode, Integer catalogId, String operation) {
		this.product = new CatalogAdminsProduct();
		this.product.setProductCode(productCode);
		this.catalogId = catalogId;
		this.operation = operation;
	}

	public ProductWrapper(ProductVariation variation, String parentProductCode, String variationKey, Integer catalogId, String operation) {
		this.variation = variation;
		this.parentProductCode = parentProductCode;
		this.variationKey = variationKey;
		this.catalogId = catalogId;
		this.operation = operation;
	}

	public ProductWrapper(ProductVariation variation, String parentProductCode, String variationKey, Integer catalogId) {
		this(variation, parentProductCode, variationKey, catalogId, "Create");
	}

	public ProductWrapper(String variationProductCode, String parentProductCode, String variationKey, Integer catalogId, String operation) {
		this.variation = new ProductVariation();
		this.variation.setVariationProductCode(variationProductCode);
		this.parentProductCode = parentProductCode;
		this.variationKey = variationKey;
		this.catalogId = catalogId;
		this.operation = operation;
	}
	
	public ProductWrapper(CatalogAdminsProduct product, String operation) {
		this.product = product;
		this.operation = operation;
	}

	public CatalogAdminsProduct getProduct() {
		return product;
	}

	public ProductVariation getVariation() {
		return variation;
	}

	public String getParentProductCode() {
		return parentProductCode;
	}

	public String getVariationKey() {
		return variationKey;
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public String getOperation() {
		return operation;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
}