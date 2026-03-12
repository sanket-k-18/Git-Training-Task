package com.ignitiv.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.dto.ProductDTO;
import com.ignitiv.service.KiboApiService;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.AttributeVocabularyValueInProductType;
import com.kibocommerce.sdk.catalogadministration.models.AttributeVocabularyValueLocalizedContent;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttributeCollection;
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
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationOption;
import com.kibocommerce.sdk.common.ApiException;

@Component
public class Helpers {

	@Autowired
	private KiboApiService service;

	private final Map<String, Integer> productTypeCache = new ConcurrentHashMap<>();
	private final Map<Integer, Set<String>> productTypeAttributeCache = new ConcurrentHashMap<>();
	private final Map<String, Map<Object, Integer>> attributeSequenceCache = new ConcurrentHashMap<>();
	private final Map<String, CatalogAdminsAttribute> attributeCache = new ConcurrentHashMap<>();
	private final Set<String> ensuredAttributes = ConcurrentHashMap.newKeySet();

	private final ConcurrentHashMap<String, ReentrantLock> productTypeLocks = new ConcurrentHashMap<>();
	private final List<String> optionColumns = new ArrayList<>();


	public static class AttributeDefinition {
		public final String name;
		public final String value;
		public final String type;

		public AttributeDefinition(String name, String value, String type) {
			this.name = name;
			this.value = value;
			this.type = type;
		}
	}



	public void preloadAll() throws ApiException {
		preloadProductTypes();
		preloadAttributes();
	}

	private void preloadProductTypes() throws ApiException {
		System.out.println("Loading product types ...");
		int startIndex = 0;
		int pageSize = 50;
		int total = Integer.MAX_VALUE;

		while (startIndex < total) {
			ProductTypeCollection response = service.getProductTypes(startIndex, pageSize);
			if (response.getItems() == null || response.getItems().isEmpty())
				break;

			total = response.getTotalCount() != null ? response.getTotalCount() : 0;

			for (ProductType type : response.getItems()) {
				productTypeCache.put(type.getName().trim(), type.getId());

				Set<String> attrSet = productTypeAttributeCache.computeIfAbsent(type.getId(),k -> ConcurrentHashMap.newKeySet());

				if (type.getOptions() != null)
					type.getOptions().forEach(a -> attrSet.add(a.getAttributeFQN()));
				if (type.getProperties() != null)
					type.getProperties().forEach(a -> attrSet.add(a.getAttributeFQN()));
				if (type.getExtras() != null)
					type.getExtras().forEach(a -> attrSet.add(a.getAttributeFQN()));
			}
			startIndex += pageSize;
		}
		System.out.println("Product types loaded: " + productTypeCache.size());
	}

	private void preloadAttributes() throws ApiException {
		System.out.println("Loading attributes...");
		int startIndex = 0;
		int pageSize = 200;
		int total = Integer.MAX_VALUE;

		while (startIndex < total) {
			CatalogAdminsAttributeCollection response = service.getAttributes(startIndex, pageSize);
			if (response.getItems() == null || response.getItems().isEmpty())
				break;

			total = response.getTotalCount() != null ? response.getTotalCount() : 0;

			for (CatalogAdminsAttribute attr : response.getItems()) {
				String fqn = attr.getAttributeFQN();
				if (fqn == null)
					continue;

				attributeCache.put(fqn, attr);

				if (attr.getVocabularyValues() != null) {
					Map<Object, Integer> seq = new HashMap<>();
					for (CatalogAdminsAttributeVocabularyValue v : attr.getVocabularyValues()) {
						seq.put(v.getValue(), v.getValueSequence());
					}
					attributeSequenceCache.put(fqn, seq);
				}
			}
			startIndex += pageSize;
		}
		System.out.println("Attributes loaded: " + attributeCache.size());
	}


	public void ensureAllAttributesAttached(Integer productTypeId, String productTypeName,List<AttributeDefinition> attributes) throws Exception {
		ReentrantLock lock = productTypeLocks.computeIfAbsent(productTypeName, k -> new ReentrantLock());
		lock.lock();
		try {
			for (AttributeDefinition attr : attributes) {
				ensureAttributeAttachedInternal(productTypeId, attr.name, attr.value, attr.type);
			}
		} finally {
			lock.unlock();
		}
	}

	private void ensureAttributeAttachedInternal(Integer productTypeId, String attrName, String value, String type)throws Exception {
		String attrFQN = "tenant~" + attrName;
		String cacheKey = productTypeId + "-" + attrFQN;

		if (ensuredAttributes.contains(cacheKey))
			return;

		CatalogAdminsAttribute attribute = getOrFetchAttribute(attrFQN);
		if (attribute == null) {
			attribute = createAttribute(attrName, type, value);
		}
		if (!isProductTypeAttributeExist(productTypeId, attrFQN)) {
			addAttributeToProductType(productTypeId, attrFQN, value, type, attribute);
			productTypeAttributeCache.computeIfAbsent(productTypeId, k -> ConcurrentHashMap.newKeySet()).add(attrFQN);
			System.out.println("Attach " + attrFQN + " → productType " + productTypeId);
		}

		ensuredAttributes.add(cacheKey);
	}


	public boolean isProductTypeExist(String productTypeName) {
		return productTypeCache.containsKey(productTypeName);
	}

	public boolean isProductTypeAttributeExist(Integer productTypeId, String fqn) {
		return productTypeAttributeCache.getOrDefault(productTypeId, Collections.emptySet()).contains(fqn);
	}

	public Integer getProductTypeIdByName(String name) {
		return productTypeCache.getOrDefault(name, null);
	}

	public synchronized ProductType createProductType(Integer masterCatId, String name, List<String> usage)throws ApiException {
		if (productTypeCache.containsKey(name.trim())) {
			ProductType stub = new ProductType();
			stub.setId(productTypeCache.get(name.trim()));
			stub.setName(name);
			return stub;
		}

		ProductType productType = new ProductType();
		productType.setMasterCatalogId(masterCatId);
		productType.setName(name);
		productType.setProductUsages(usage);

		ProductType created = service.createProductType(productType);
		productTypeCache.put(name.trim(), created.getId());
		productTypeAttributeCache.put(created.getId(), ConcurrentHashMap.newKeySet());
		System.out.println("ProductType Created: " + name);
		return created;
	}

	public CatalogAdminsAttribute getOrFetchAttribute(String fqn) throws ApiException {
		if (attributeCache.containsKey(fqn))
			return attributeCache.get(fqn);
		try {
			CatalogAdminsAttribute attr = service.getAttribute(fqn);
			attributeCache.put(fqn, attr);
			return attr;
		} catch (ApiException e) {
			if (e.getCode() == 404)
				return null;
			throw e;
		}
	}

	public CatalogAdminsAttribute createAttribute(String attributeName, String attrType, String attrValue)
			throws ApiException {
		CatalogAdminsAttribute attribute = new CatalogAdminsAttribute();
		attribute.setAdminName(attributeName);
		attribute.setAttributeCode(attributeName);
		findAndSetAttributeType(attribute, attrValue);

		if ("List".equalsIgnoreCase(attribute.getInputType()) && "Predefined".equalsIgnoreCase(attribute.getValueType())) {
			List<CatalogAdminsAttributeVocabularyValue> vocabList = new ArrayList<>();
			for (String value : attrValue.split(";")) {
				CatalogAdminsAttributeVocabularyValue vocab = new CatalogAdminsAttributeVocabularyValue();
				AttributeVocabularyValueLocalizedContent content = new AttributeVocabularyValueLocalizedContent();
				vocab.setValue(value.trim());
				content.setStringValue(value.trim());
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
			break;
		default:
			throw new IllegalArgumentException("Invalid attribute type: " + attrType);
		}

		CatalogAdminsAttribute created = service.createAttribute(attribute);
		attributeCache.put("tenant~" + attributeName, created);
		System.out.println("Attribute Created: tenant~" + attributeName);
		return created;
	}

	public AttributeInProductType addAttributeToProductType(Integer productTypeId, String attrFQN, String values,String attrType, CatalogAdminsAttribute attr) throws ApiException {
		AttributeInProductType attribute = new AttributeInProductType();
		attribute.setAttributeFQN(attrFQN);
		attribute.setIsRequiredByAdmin(false);
		attribute.setOrder(0);
		attribute.setAttributeDetail(attr);

		if ("List".equalsIgnoreCase(attr.getInputType()) && "Predefined".equalsIgnoreCase(attr.getValueType())) {
			for (String value : values.split(";")) {
				AttributeVocabularyValueInProductType attrValue = new AttributeVocabularyValueInProductType();
				attrValue.setValue(value.trim());
				attribute.addVocabularyValuesItem(attrValue);
			}
		}
		return service.addAttrToProductType(productTypeId, attribute, attrType);
	}

	public CatalogAdminsProductOption buildOption(String attributeFQN, String csvValue) {
		CatalogAdminsProductOption option = new CatalogAdminsProductOption();
		option.setAttributeFQN(attributeFQN);
		List<CatalogAdminsProductOptionValue> values = new ArrayList<>();
		for (String val : csvValue.split(";")) {
			CatalogAdminsProductOptionValue v = new CatalogAdminsProductOptionValue();
			v.setValue(val.trim());
			values.add(v);
		}
		option.setValues(values);
		return option;
	}

	public CatalogAdminsProductProperty buildProperty(String fqn, String value) {
		CatalogAdminsProductProperty property = new CatalogAdminsProductProperty();
		property.setAttributeFQN(fqn);
		List<CatalogAdminsProductPropertyValue> values = new ArrayList<>();
		for (String val : value.split(";")) {
			CatalogAdminsProductPropertyValue pv = new CatalogAdminsProductPropertyValue();
			ProductPropertyValueLocalizedContent c = new ProductPropertyValueLocalizedContent();
			c.setStringValue(val.trim());
			pv.setContent(c);
			pv.setValue(val.trim());
			values.add(pv);
		}
		property.setValues(values);
		return property;
	}

	public ProductExtra buildExtra(String fqn, String value) {
		ProductExtra extra = new ProductExtra();
		extra.setAttributeFQN(fqn);
		ProductExtraValue ev = new ProductExtraValue();
		ev.setValue(value.contains(";") ? value.split(";")[0].trim() : value.trim());
		extra.setValues(Collections.singletonList(ev));
		return extra;
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

	public void setOptionColumns(List<String> headers) {
		optionColumns.clear();
		for (String header : headers) {
			if (header.trim().endsWith("Options") || header.trim().endsWith("Option")) {
				optionColumns.add(header.trim());
			}
		}
	}

	public Map<String, String> extractOptions(ProductDTO product) {
		Map<String, String> options = new LinkedHashMap<>();
		for (String column : optionColumns) {
			String value = product.getFieldValue(column);
			if (value != null && !value.isBlank()) {
				String attrName = column.replace("Options", "").replace("Option", "");
				options.put(attrName, value);
			}
		}
		return options;
	}

	public ProductInCatalogInfo addOrUpdateProductToCatalog(String productCode, Integer catalogId, Boolean isActive, String operation) throws ApiException {
		ProductInCatalogInfo productInfo = new ProductInCatalogInfo();
		
		productInfo.setCatalogId(catalogId);
		productInfo.setIsActive(isActive);
		
		switch(operation) {
		case "Create":
		case "create":
			return  service.addProductInCatalog(productCode, productInfo);
		case "Update":
		case "update":
			return  service.updateProductInCatalog(productCode, catalogId, productInfo);
		}
		
		return null;
	}


	public Map<Object, Integer> getAttributeSequence(String fqn) throws ApiException {
		if (attributeSequenceCache.containsKey(fqn))
			return attributeSequenceCache.get(fqn);
		CatalogAdminsAttribute attr = getOrFetchAttribute(fqn);
		Map<Object, Integer> sequence = new HashMap<>();
		if (attr != null && attr.getVocabularyValues() != null) {
			for (CatalogAdminsAttributeVocabularyValue v : attr.getVocabularyValues()) {
				sequence.put(v.getValue(), v.getValueSequence());
			}
		}
		attributeSequenceCache.put(fqn, sequence);
		return sequence;
	}

	private void findAndSetAttributeType(CatalogAdminsAttribute attribute, String attrValue) {
		if (attrValue.contains(";") && !attrValue.toLowerCase().contains("yes;no")) {
			attribute.setInputType("List");
			attribute.setDataType("String");
			attribute.setValueType("Predefined");
			return;
		}
		if (attrValue.toLowerCase().contains("yes;no")) {
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
		} catch (Exception ignored) {
		}
		try {
			LocalDate.parse(attrValue);
			attribute.setInputType("Date");
			attribute.setDataType("DateTime");
			attribute.setValueType("ShopperEntered");
			return;
		} catch (Exception ignored) {
		}

		attribute.setInputType("TextBox");
		attribute.setDataType("String");
		attribute.setValueType("AdminEntered");
	}
}