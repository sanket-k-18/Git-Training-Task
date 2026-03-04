package com.ignitiv.service;

import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;

public class ProductWrapper {

    private CatalogAdminsProduct product;
    private ProductVariation variation;
    private String variationKey;
    private String parentProductCode;
    private Integer catalogId;

    public ProductWrapper(CatalogAdminsProduct product, Integer catalogId) {
        this.product = product;
        this.catalogId = catalogId;
    }

    public ProductWrapper(ProductVariation variation, String parentProductCode, String variationKey, Integer catalogId) {
        this.variation = variation;
        this.parentProductCode = parentProductCode;
        this.variationKey = variationKey;
        this.catalogId = catalogId;
    }

    public CatalogAdminsProduct getProduct() {
        return product;
    }

    public ProductVariation getVariation() {
        return variation;
    }

    public String getVariationKey() {
        return variationKey;
    }

    public String getParentProductCode() {
        return parentProductCode;
    }

    public Integer getCatalogId() {
        return catalogId;
    }
}
