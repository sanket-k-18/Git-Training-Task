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
import com.ignitiv.util.Helpers.AttributeDefinition;
import com.kibocommerce.sdk.catalogadministration.models.*;

@Component
public class ParentProcessor implements ItemProcessor<ProductDTO, ProductWrapper> {

    @Autowired
    private Helpers helper;

    @Override
    public ProductWrapper process(ProductDTO item) throws Exception {

        if (item.getParentProductCode() != null && !item.getParentProductCode().isBlank()) {
            return null;
        }

        CatalogAdminsProduct product = new CatalogAdminsProduct();
        String operation = item.getOperations();

        if (operation.equalsIgnoreCase("Delete")) {
            product.setProductCode(item.getProductCode());
            return new ProductWrapper(product, operation);
        }

     
        product.setProductCode(item.getProductCode());
        product.setMasterCatalogId(item.getMasterCatalogId());
        product.setIsTaxable(item.getIsTaxable());
        product.setUpc(item.getUpc());

        ProductLocalizedContent content = new ProductLocalizedContent();
        content.setProductName(item.getProductName());
        content.setProductShortDescription(item.getShortDescription());
        content.setProductFullDescription(item.getLongDescription());
        product.setContent(content);

        CatalogAdminsProductPrice price = new CatalogAdminsProductPrice();
        price.setPrice(item.getPrice());
        price.setSalePrice(item.getSalePrice());
        price.setMsrp(item.getMsrp());
        product.setPrice(price);

        CatalogAdminsProductInventoryInfo inventoryInfo = new CatalogAdminsProductInventoryInfo();
        inventoryInfo.setManageStock(item.getManageStock());
        inventoryInfo.setOutOfStockBehavior(item.getOutOfStockBehavior());
        product.setInventoryInfo(inventoryInfo);

        CommerceRuntimeMeasurement packageWeight = new CommerceRuntimeMeasurement();
        packageWeight.setUnit(item.getPackageWeightUnit());
        packageWeight.setValue(item.getPackageWeightValue());
        product.setPackageWeight(packageWeight);

        CommerceRuntimeMeasurement packageHeight = new CommerceRuntimeMeasurement();
        packageHeight.setUnit(item.getPackageHeightUnit());
        packageHeight.setValue(item.getPackageHeightValue());
        product.setPackageHeight(packageHeight);

        CommerceRuntimeMeasurement packageWidth = new CommerceRuntimeMeasurement();
        packageWidth.setUnit(item.getPackageWidthUnit());
        packageWidth.setValue(item.getPackageWidthValue());
        product.setPackageWidth(packageWidth);

        CommerceRuntimeMeasurement packageLength = new CommerceRuntimeMeasurement();
        packageLength.setUnit(item.getPackageLengthUnit());
        packageLength.setValue(item.getPackageLengthValue());
        product.setPackageLength(packageLength);

        boolean isConfigurable = "Configurable".equalsIgnoreCase(item.getProductUsage());

    
        List<String> productUsageList = new ArrayList<>();
        productUsageList.add("Standard");
        if (isConfigurable) productUsageList.add("Configurable");

		if (!helper.isProductTypeExist(item.getProductType())) {
			ProductType productType = helper.createProductType(item.getMasterCatalogId(), item.getProductType(), productUsageList);
            product.setProductTypeId(productType.getId());
        } else {
            product.setProductTypeId(helper.getProductTypeIdByName(item.getProductType()));
        }

        List<AttributeDefinition> attributesToAttach = new ArrayList<>();
        attributesToAttach.add(new AttributeDefinition("brand",item.getBrand(),"property"));
        attributesToAttach.add(new AttributeDefinition("gift-wrap",item.getGiftWrap(),"extra"));

        if (isConfigurable) {
            Map<String, String> optionsMap = helper.extractOptions(item);
            for (Map.Entry<String, String> entry : optionsMap.entrySet()) {
                attributesToAttach.add(new AttributeDefinition(entry.getKey(), entry.getValue(), "option"));
            }
        }
        helper.ensureAllAttributesAttached(product.getProductTypeId(),item.getProductType(), attributesToAttach);

        
        List<CatalogAdminsProductProperty> properties = new ArrayList<>();
        properties.add(helper.buildProperty("tenant~brand", item.getBrand()));
        product.setProperties(properties);

        List<ProductExtra> extras = new ArrayList<>();
        extras.add(helper.buildExtra("tenant~gift-wrap", item.getGiftWrap()));
        product.setExtras(extras);

        if (isConfigurable) {
            Map<String, String> optionsMap = helper.extractOptions(item);
            List<CatalogAdminsProductOption> options = new ArrayList<>();
            List<ProductVariationOption>     drivers = new ArrayList<>();

            for (Map.Entry<String, String> entry : optionsMap.entrySet()) {
                String attrFQN = "tenant~" + entry.getKey();
                options.add(helper.buildOption(attrFQN, entry.getValue()));

                ProductVariationOption driver = new ProductVariationOption();
                driver.setAttributeFQN(attrFQN);
                drivers.add(driver);
            }

            product.setOptions(options);
            product.setVariationOptions(drivers);
            product.setHasConfigurableOptions(true);
            product.setHasStandAloneOptions(false);
            product.setIsVariation(false);

        } else {
            product.setHasConfigurableOptions(false);
            product.setHasStandAloneOptions(false);
            product.setIsVariation(false);
        }

        product.setProductUsage(item.getProductUsage());
        
        return new ProductWrapper(product, item.getCatalogId(), operation, item.getIsActive());
    }
}