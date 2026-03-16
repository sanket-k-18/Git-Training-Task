package com.ignitiv.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.dto.ProductDTO;
import com.ignitiv.service.ProductWrapper;
import com.ignitiv.util.Helpers;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductProperty;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductPropertyValue;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationOption;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationProperty;

@Component
public class VariationProcessor implements ItemProcessor<ProductDTO, ProductWrapper> {

    @Autowired
    private Helpers helper;

    @Override
    public ProductWrapper process(ProductDTO item) throws Exception {

        boolean isVariant = item.getParentProductCode() != null && !item.getParentProductCode().isBlank();

        if (!isVariant) {
            return null; 
        }
      
        Map<String, String> optionsMap = helper.extractOptions(item);
        List<String> sequences = new ArrayList<>();
        List<ProductVariationOption> variationOptions = new ArrayList<>();

        for (Map.Entry<String, String> entry : optionsMap.entrySet()) {

            String optionName = entry.getKey();
            String attrFQN = "tenant~" + optionName;
            String value = entry.getValue();

            Map<Object, Integer> attributeSequence = helper.getAttributeSequence(attrFQN);
            Integer seq = attributeSequence.get(value);
           
            sequences.add(seq != null ? seq.toString() : null);
            
            ProductVariationOption option = new ProductVariationOption();
            option.setAttributeFQN(attrFQN);
            option.setValue(value);

            variationOptions.add(option);
        }

        String variationKey = String.join("-", sequences);
        
        
        
        List<ProductVariationProperty> properties = new ArrayList<>();
        properties.add(helper.buildVariationProperty("tenant~modelNumber", item.getModelNumber()));

        ProductVariation variation = new ProductVariation();

        variation.setVariationProductCode(item.getProductCode());
        variation.setOptions(variationOptions);
        variation.setIsActive(true);
        variation.setProperties(properties);
           
        
        return new ProductWrapper(variation,item.getParentProductCode(),variationKey,item.getCatalogId(), item.getOperations());
    }
}