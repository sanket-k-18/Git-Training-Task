package com.ignitiv.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.dto.ProductDTO;
import com.ignitiv.service.KiboApiService;
import com.kibocommerce.sdk.catalogadministration.api.ProductAttributesApi;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.AttributeVocabularyValueInProductType;
import com.kibocommerce.sdk.catalogadministration.models.AttributeVocabularyValueLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttributeVocabularyValue;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductOption;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductOptionValue;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductProperty;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductPropertyValue;
import com.kibocommerce.sdk.catalogadministration.models.ProductExtra;
import com.kibocommerce.sdk.catalogadministration.models.ProductExtraValue;
import com.kibocommerce.sdk.catalogadministration.models.ProductInCatalogInfo;
import com.kibocommerce.sdk.catalogadministration.models.ProductPropertyValueLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductTypeCollection;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationCollection;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationOption;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationPagedCollection;
import com.kibocommerce.sdk.common.ApiException;

@Component
public class Helpers {

	@Autowired
	private KiboApiService service;

	private Map<String, Integer> productTypeCache = new HashMap<>();
	private Map<Integer, Set<String>> productTypeAttributeCache = new HashMap<>();
	private boolean productTypesLoaded = false;
	private boolean productTypesAttributeLoaded = false;

	public boolean isAttributeExist(String fqn) throws ApiException {

		try {
			CatalogAdminsAttribute attr = service.getAttribute(fqn);
			return attr != null;
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				return false;
			}
			throw new RuntimeException(e);
		}

	}

	private void loadProductTypes() throws ApiException {

		if (productTypesLoaded) {
			return;
		}

		int startIndex = 0;
		int pageSize = 50;

		while (true) {

			ProductTypeCollection response = service.getProductTypes(startIndex, pageSize);

			if (response.getItems() == null || response.getItems().isEmpty()) {
				break;
			}

			for (ProductType type : response.getItems()) {
				productTypeCache.put(type.getName().trim(), type.getId());
			}

			startIndex += pageSize;

			if (startIndex >= response.getTotalCount()) {
				break;
			}
		}

		productTypesLoaded = true;

	}

	private void loadProductTypesAttributes() throws ApiException {

		if (productTypesAttributeLoaded) {
			return;
		}

		System.out.println("Loading product type attributes from API into cache");

		int startIndex = 0;
		int pageSize = 50;

		while (true) {
			ProductTypeCollection response = service.getProductTypes(startIndex, pageSize);
			if (response.getItems() == null || response.getItems().isEmpty()) {
				break;
			}
			for (ProductType type : response.getItems()) {
				Set<String> attributeSet = productTypeAttributeCache.computeIfAbsent(type.getId(),k -> new HashSet<>());

				if (type.getOptions() != null) {
					for (AttributeInProductType attr : type.getOptions()) {
						attributeSet.add(attr.getAttributeFQN());
					}
				}

				if (type.getProperties() != null) {
					for (AttributeInProductType attr : type.getProperties()) {
						attributeSet.add(attr.getAttributeFQN());
					}
				}

				if (type.getExtras() != null) {
					for (AttributeInProductType attr : type.getExtras()) {
						attributeSet.add(attr.getAttributeFQN());
					}
				}
			}

			startIndex += pageSize;

			if (startIndex >= response.getTotalCount()) {
				break;
			}
		}

		productTypesAttributeLoaded = true;
	}

	private void refreshProductTypeCache() throws ApiException {
		productTypeCache.clear();
		productTypesLoaded = false;
		loadProductTypes();
	}

	public boolean isProductTypeExist(String productTypeName) {
		try {
			loadProductTypes();
			boolean flag = productTypeCache.containsKey(productTypeName);
			return flag;
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isProductTypeAttributeExist(Integer productTypeId, String fqn) {
		try {
			loadProductTypesAttributes();
			return productTypeAttributeCache.getOrDefault(productTypeId, Collections.emptySet()).contains(fqn);
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, String> extractOptions(ProductDTO product)throws IllegalArgumentException, IllegalAccessException {
		Map<String, String> options = new HashMap<>();

		for (Field field : ProductDTO.class.getDeclaredFields()) {
			String fieldName = field.getName();
			if (fieldName.endsWith("Options") || fieldName.endsWith("Option")) {
				field.setAccessible(true);
				String value = (String) field.get(product);

				if (value != null && !value.isBlank()) {
					String attrName = fieldName.replace("Options", "").replace("Option", "");
					options.put(attrName, value);

				}
			}
		}
		return options;
	}

	public CatalogAdminsAttribute createAttribute(String attributeName, String attrType, String attrValue)throws ApiException {
		System.out.println("-----------------------------------Creating " + attributeName+ " Attribute-----------------------------------------");
		CatalogAdminsAttribute attribute = new CatalogAdminsAttribute();
		attribute.setAdminName(attributeName);
		attribute.setAttributeCode(attributeName);
		findAndSetAttributeType(attribute, attrValue);

		if ("List".equalsIgnoreCase(attribute.getInputType())&& "Predefined".equalsIgnoreCase(attribute.getValueType())) {

			List<CatalogAdminsAttributeVocabularyValue> vocabList = new ArrayList<>();
			for (String value : attrValue.split(";")) {
				CatalogAdminsAttributeVocabularyValue vocab = new CatalogAdminsAttributeVocabularyValue();
				AttributeVocabularyValueLocalizedContent content = new AttributeVocabularyValueLocalizedContent();
				vocab.setValue(value);
				content.setStringValue(value);

				vocab.setContent(content);

				vocabList.add(vocab);
			}

			attribute.setVocabularyValues(vocabList);

		}

		switch (attrType) {
		case "option":
			attribute.setIsOption(true);
			attribute.setIsExtra(false);
			attribute.setIsProperty(false);
			break;
		case "property":
			attribute.setIsProperty(true);
			attribute.setIsExtra(false);
			attribute.setIsOption(false);
			break;
		case "extra":
			attribute.setIsExtra(true);
			attribute.setIsOption(false);
			attribute.setIsProperty(false);
			;
			break;
		default:
			throw new IllegalArgumentException("Invalid attribute type");
		}
		return service.createAttribute(attribute);
	}

	public CatalogAdminsProductOption buildOption(String attributeFQN, String csvValue) {

		CatalogAdminsProductOption option = new CatalogAdminsProductOption();
		option.setAttributeFQN(attributeFQN);
		List<CatalogAdminsProductOptionValue> values = new ArrayList<>();
		for (String val : csvValue.split(";")) {
			CatalogAdminsProductOptionValue optionValue = new CatalogAdminsProductOptionValue();
			optionValue.setValue(val.trim());
			values.add(optionValue);
		}

		option.setValues(values);
		return option;
	}

	public CatalogAdminsProductProperty buildProperty(String fqn, String value) {
		CatalogAdminsProductProperty property = new CatalogAdminsProductProperty();
		property.setAttributeFQN(fqn);

		List<CatalogAdminsProductPropertyValue> values = new ArrayList<>();
		for (String val : value.split(";")) {
			CatalogAdminsProductPropertyValue propertyValue = new CatalogAdminsProductPropertyValue();
			ProductPropertyValueLocalizedContent contentValue = new ProductPropertyValueLocalizedContent();
			contentValue.setStringValue(val.trim());
			propertyValue.setContent(contentValue);
			propertyValue.setValue(val.trim());
			values.add(propertyValue);
		}
		property.setValues(values);
		return property;
	}

	public ProductExtra buildExtra(String fqn, String value) {
		ProductExtra extra = new ProductExtra();
		extra.setAttributeFQN(fqn);

		ProductExtraValue values = new ProductExtraValue();
		if (value.contains(";")) {
			String first = value.split(";")[0];
			values.setValue(first.trim());
		}
		extra.setValues(Collections.singletonList(values));
		return extra;
	}

	public AttributeInProductType addAttributeToProductType(Integer productTypeId, String attrFQN, String values,
			String attrType, CatalogAdminsAttribute attr) throws ApiException {

		AttributeInProductType attribute = new AttributeInProductType();

		attribute.setAttributeFQN(attrFQN);
		attribute.setIsRequiredByAdmin(false);
		attribute.setOrder(0);
		attribute.setAttributeDetail(attr);

		if ("List".equalsIgnoreCase(attr.getInputType()) && "Predefined".equalsIgnoreCase(attr.getValueType())) {
			for (String value : values.split(";")) {
				AttributeVocabularyValueInProductType attrValue = new AttributeVocabularyValueInProductType();
				attrValue.setValue(value);
				attribute.addVocabularyValuesItem(attrValue);

			}
		}

		return service.addAttrToProductType(productTypeId, attribute, attrType);
	}

	private void findAndSetAttributeType(CatalogAdminsAttribute attribute, String attrValue) {

		if (attrValue.contains(";") && !attrValue.contains("yes;no")) {
			attribute.setInputType("List");
			attribute.setDataType("String");
			attribute.setValueType("Predefined");
			return;
		}
		if (attrValue.contains("yes;no")) {
			attribute.setInputType("YesNo");
			attribute.setDataType("Bool");
			attribute.setValueType("ShopperEntered");
			return;
		}

		try {
			Double.parseDouble(attrValue);
			attribute.setInputType("TextBox");
			attribute.setDataType("Number");
			attribute.setValueType("ShopperEntered");
			return;
		} catch (Exception e) {
		}

		try {
			LocalDate.parse(attrValue);
			attribute.setInputType("Date");
			attribute.setDataType("DateTime");
			attribute.setValueType("ShopperEntered");
			return;
		} catch (Exception e) {
		}

		attribute.setInputType("TextBox");
		attribute.setDataType("String");
		attribute.setValueType("AdminEntered");

	}

	public ProductVariationOption buildVariationOption(String fqn, String value) {
		
		ProductVariationOption option = new ProductVariationOption();
		option.setAttributeFQN(fqn);
		option.setValue(value);

		AttributeVocabularyValueLocalizedContent content = new AttributeVocabularyValueLocalizedContent();
		content.setStringValue(value);
		content.setLocaleCode("en-US");

		option.setContent(content);

		return option;
	}

	public ProductType createProductType(Integer masterCatId, String name, List<String> usage) throws ApiException {
		ProductType productType = new ProductType();
		productType.setMasterCatalogId(masterCatId);
		productType.setName(name);
		productType.setProductUsages(usage);

		ProductType created = service.createProductType(productType);
		refreshProductTypeCache();

		return created;

	}

	public Integer getProductTypeIdByName(String name) {
		if (productTypeCache.containsKey(name)) {
			return productTypeCache.get(name);
		}
		return null;
	}

	public CatalogAdminsAttribute getAttribute(String fqn) throws ApiException {
		return service.getAttribute(fqn);
	}

	public void ensureAttributeAttached(Integer productTypeId, String attrName, String value, String type)throws Exception {

		String attrFQN = "tenant~" + attrName;
		CatalogAdminsAttribute attribute;

		if (!isAttributeExist(attrFQN)) {
			attribute = createAttribute(attrName, type, value);
		} else {
			attribute = getAttribute(attrFQN);
		}

		try {
			if (!isProductTypeAttributeExist(productTypeId, attrFQN)) {
				System.out.println("---------------------Attaching " + attrFQN + " to product Type " + productTypeId + "-----------------------------");
				addAttributeToProductType(productTypeId, attrFQN, value, type, attribute);
				productTypeAttributeCache.clear();
				productTypesAttributeLoaded = false;
			} else {
				System.out.println("---------------------Attribute " + attrFQN + " is already attached to product Type "+ productTypeId + "----------------------------");
			}
		} catch (Exception e) {
		}
	}

	public ProductInCatalogInfo addProductToCatalog(String productCode, Integer catalogId) throws ApiException {

		System.out.println("Adding Product " + productCode + " to Catalog "+ catalogId);
		ProductInCatalogInfo productInfo = new ProductInCatalogInfo();
		productInfo.setCatalogId(catalogId);
		productInfo.setIsActive(true);
		return service.addProductInCatalog(productCode, productInfo);
	}


	public Map<Object, Integer> getAttributeSequence(String fqn) throws ApiException {
		Map<Object, Integer> sequence = new HashMap<>();
		CatalogAdminsAttribute attr = service.getAttribute(fqn);
		for(CatalogAdminsAttributeVocabularyValue value : attr.getVocabularyValues()) {
			sequence.put(value.getValue(), value.getValueSequence());
		}
		
		return sequence;

	}
}
