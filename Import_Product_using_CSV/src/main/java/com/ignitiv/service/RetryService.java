package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.ignitiv.exception.ApiRuntimeException;
import com.ignitiv.exception.RateLimitException;
import com.ignitiv.util.Helpers;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class RetryService {

    @Autowired
    private KiboApiService service;

    @Autowired
    private Helpers helper;

 
    private boolean handleApiException(ApiException e, String context) {
        int code = e.getCode();
        switch (code) {
            case 429 -> throw new RateLimitException(e);                      
            case 0, 503 -> throw new ApiRuntimeException(e);
            case 404 -> { System.out.println("Not found, skipping: " + context); return true; }  // skip
            default -> { System.out.println("Non-retryable error [" + code + "], skipping: " + context + " - " + e.getMessage()); return true; }  // skip 400, 500, etc.
        }
    }

    @Retryable(retryFor = {RateLimitException.class, ApiRuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void createProduct(CatalogAdminsProduct product) {
        try {
            service.addProduct(product);
            System.out.println("Product created: " + product.getProductCode());
        } catch (ApiException e) {
            handleApiException(e, product.getProductCode());
        }
    }

    @Retryable(retryFor = {RateLimitException.class, ApiRuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void updateProduct(String productCode, CatalogAdminsProduct product) {
        try {
            service.updateProduct(productCode, product);
            System.out.println("Product updated: " + productCode);
        } catch (ApiException e) {
            handleApiException(e, productCode);
        }
    }

    @Retryable(retryFor = {RateLimitException.class, ApiRuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void deleteProduct(String productCode) {
        try {
            service.deleteProduct(productCode);
            System.out.println("Product deleted: " + productCode);
        } catch (ApiException e) {
            handleApiException(e, productCode);
        }
    }

    @Retryable(retryFor = {RateLimitException.class, ApiRuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void addOrUpdateProductToCatalog(String productCode, Integer catalogId, Boolean isActive, String operation) {
        try {
            helper.addOrUpdateProductToCatalog(productCode, catalogId, isActive, operation);
        } catch (ApiException e) {
            handleApiException(e, productCode);
        }
    }
    
 

    @Retryable(retryFor = {RateLimitException.class, ApiRuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void updateVariation(String parentProductCode, String variationKey, ProductVariation variation) {
        try {
            service.updateVariation(parentProductCode, variationKey, variation);
            System.out.println("Variation updated: " + variationKey);
        } catch (ApiException e) {
            handleApiException(e, variationKey);
        }
    }


    @Recover
    public void recoverCreateProduct(RateLimitException e, CatalogAdminsProduct product) {
        System.out.println("createProduct exhausted retries (429): " + product.getProductCode());
    }

    @Recover
    public void recoverUpdateProduct(RateLimitException e, String productCode, CatalogAdminsProduct product) {
        System.out.println("updateProduct exhausted retries (429): " + productCode);
    }

    @Recover
    public void recoverDeleteProduct(RateLimitException e, String productCode) {
        System.out.println("deleteProduct exhausted retries (429): " + productCode);
    }

    @Recover
    public void recoverAddProductToCatalog(RateLimitException e, String productCode, Integer catalogId) {
        System.out.println("addProductToCatalog exhausted retries (429): " + productCode);
    }

    @Recover
    public void recoverUpdateVariation(RateLimitException e, String parentProductCode, String variationKey, ProductVariation variation) {
        System.out.println("updateVariation exhausted retries (429): " + variationKey);
    }


    @Recover
    public void recoverCreateProduct(ApiRuntimeException e, CatalogAdminsProduct product) {
        System.out.println("createProduct exhausted retries (timeout/503): " + product.getProductCode());
    }

    @Recover
    public void recoverUpdateProduct(ApiRuntimeException e, String productCode, CatalogAdminsProduct product) {
        System.out.println("updateProduct exhausted retries (timeout/503): " + productCode);
    }

    @Recover
    public void recoverDeleteProduct(ApiRuntimeException e, String productCode) {
        System.out.println("deleteProduct exhausted retries (timeout/503): " + productCode);
    }

    @Recover
    public void recoverAddProductToCatalog(ApiRuntimeException e, String productCode, Integer catalogId) {
        System.out.println("addProductToCatalog exhausted retries (timeout/503): " + productCode);
    }

    @Recover
    public void recoverUpdateVariation(ApiRuntimeException e, String parentProductCode, String variationKey, ProductVariation variation) {
        System.out.println("updateVariation exhausted retries (timeout/503): " + variationKey);
    }
}