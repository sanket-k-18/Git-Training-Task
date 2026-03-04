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
import com.kibocommerce.sdk.catalogadministration.models.AttributeVocabularyValueLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductInventoryInfo;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductOption;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductPrice;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductProperty;
import com.kibocommerce.sdk.catalogadministration.models.CommerceRuntimeMeasurement;
import com.kibocommerce.sdk.catalogadministration.models.ProductExtra;
import com.kibocommerce.sdk.catalogadministration.models.ProductLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationOption;

@Component
public class Processor implements ItemProcessor<ProductDTO, ProductWrapper> {

	@Autowired
	private Helpers helper;

	@Override
	public ProductWrapper process(ProductDTO item) throws Exception {

		CatalogAdminsProduct product = new CatalogAdminsProduct();

		boolean isVariant = item.getParentProductCode() != null && !item.getParentProductCode().isBlank();

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

		
		
		List<String> productUsage = new ArrayList<>();
		productUsage.add("Standard");
		productUsage.add("Bundle");
		productUsage.add(item.getProductUsage());
		
		if (!helper.isProductTypeExist(item.getProductType())) {
			try {
				System.out.println("----------------------------Creating ProductType--------------------------------------");

				ProductType productType = helper.createProductType(item.getMasterCatalogId(), item.getProductType(),productUsage);
				product.setProductTypeId(productType.getId());
				System.out.println("------------------------" + item.getProductType() + " Created ------------------------");

			} catch (Exception e) {
				System.out.println("Error Creating productType");
			}
		} else {
			Integer productTypeId = helper.getProductTypeIdByName(item.getProductType());
			product.setProductTypeId(productTypeId);
		}
		
		
		
		
		
		List<CatalogAdminsProductProperty> properties = new ArrayList<>();

		helper.ensureAttributeAttached(product.getProductTypeId(), "brand", item.getBrand(), "property");
		CatalogAdminsProductProperty property = helper.buildProperty("tenant~brand", item.getBrand());
		properties.add(property);
		product.setProperties(properties);

		List<ProductExtra> extras = new ArrayList<>();
		helper.ensureAttributeAttached(product.getProductTypeId(), "gift-wrap", item.getGiftWrap(), "extra");
		ProductExtra extra = helper.buildExtra("tenant~gift-wrap", item.getGiftWrap());
		extras.add(extra);
		product.setExtras(extras);
		
		
		
		
	
		
		
		Map<String, String> optionsMap = helper.extractOptions(item);

		if (isVariant) {

		    List<String> sequences = new ArrayList<>();
		    List<ProductVariationOption> variationOptions = new ArrayList<>();

		    for (Map.Entry<String, String> entry : optionsMap.entrySet()) {

		        String optionName = entry.getKey();
		        String attrFQN = "tenant~" + optionName;
		        String value = entry.getValue();
		        
		        Map<Object, Integer> attributeSequence = helper.getAttributeSequence(attrFQN);
		        Integer seq = attributeSequence.get(value);

		        sequences.add(seq.toString());

		        ProductVariationOption option = new ProductVariationOption();
		        option.setAttributeFQN(attrFQN);
		        option.setValue(value);
		        variationOptions.add(option);
		    }

		    String variationKey = String.join("-", sequences);

		    ProductVariation variation = new ProductVariation();
		    variation.setVariationProductCode(item.getProductCode());
		    variation.setOptions(variationOptions);
		    variation.setIsActive(true);
		    

		    return new ProductWrapper(variation,item.getParentProductCode(),variationKey,item.getCatalogId());
		}
		
		if(!isVariant && "Configurable".equalsIgnoreCase(item.getProductUsage())) {
		System.out.println("Product code : " +item.getProductCode());

			
		List<CatalogAdminsProductOption> options = new ArrayList<>();
		List<ProductVariationOption> parentVariationDrivers = new ArrayList<>();
		

		for (Map.Entry<String, String> entry : optionsMap.entrySet()) {
			
			String optionName = entry.getKey();
			String attrFQN = "tenant~" + optionName;
			String value = entry.getValue();
			
			
			
			helper.ensureAttributeAttached(product.getProductTypeId(), optionName, value, "option");
			CatalogAdminsProductOption option = helper.buildOption(attrFQN, value);
			options.add(option);
			
			
		    ProductVariationOption driver = new ProductVariationOption();
		    driver.setAttributeFQN(attrFQN);

		    parentVariationDrivers.add(driver);

		}
		product.setOptions(options);
		product.setVariationOptions(parentVariationDrivers);
		product.setHasConfigurableOptions(true);
		product.setHasStandAloneOptions(false);
		product.setProductUsage("Configurable");
		product.setIsVariation(false);
		}
	
		return new ProductWrapper(product, item.getCatalogId());
	}

}
