package com.ignitiv.batch;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.service.KiboApiService;
import com.ignitiv.service.ProductWrapper;
import com.ignitiv.util.Helpers;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.common.ApiException;

@Component
public class Writer implements ItemWriter<ProductWrapper>{

    @Autowired
    private KiboApiService service;

    @Autowired
    private Helpers helper;

    @Override
    public void write(Chunk<? extends ProductWrapper> chunk) throws Exception {

        for(ProductWrapper wrapper : chunk.getItems()) {

            try {

                if(wrapper.getProduct() != null) {

                    CatalogAdminsProduct product = wrapper.getProduct();
                    Integer catalogId = wrapper.getCatalogId();

                    service.addProduct(product);
                    System.out.println("Product Created : " + product.getProductCode());

                    helper.addProductToCatalog(product.getProductCode(), catalogId);

                }

                if(wrapper.getVariation() != null) {

                    ProductVariation variation = wrapper.getVariation();
                    String parentProductCode = wrapper.getParentProductCode();
                    String variationKey = wrapper.getVariationKey();

                    service.updateVariation(parentProductCode, variationKey, variation);

                    System.out.println("Variation Created : " + variationKey);
                }

            } catch(ApiException e) {
                System.out.println("API Error : " + e.getMessage());
            }
        }
    }
}