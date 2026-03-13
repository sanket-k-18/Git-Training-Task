package com.ignitiv.batch;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.exception.ApiRuntimeException;
import com.ignitiv.service.ProductWrapper;
import com.ignitiv.service.RetryService;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariation;
import com.kibocommerce.sdk.common.ApiException;

@Component
public class Writer implements ItemWriter<ProductWrapper> {

	@Autowired
	private RetryService retryService;

	private final Set<String> createdProducts = ConcurrentHashMap.newKeySet();

	@Override
	public void write(Chunk<? extends ProductWrapper> chunk) {
		for (ProductWrapper wrapper : chunk.getItems()) {
			try {

				if (wrapper.getProduct() != null) {
					CatalogAdminsProduct product = wrapper.getProduct();
					String productCode = product.getProductCode();
					Integer catalogId = wrapper.getCatalogId();
					String operation = wrapper.getOperation();
					Boolean isActive = wrapper.getIsActive();

					try {
						switch (operation.toLowerCase()) {
						case "Create":
						case "create":
							retryService.createProductWithCatalog(product, catalogId, isActive);
							createdProducts.add(productCode);
							break;
						case "Update":
						case "update":
							retryService.updateProductWithCatalog(productCode, product, catalogId, isActive);
							createdProducts.add(productCode);
							break;
						case "delete":
						case "Delete":
							retryService.deleteProduct(productCode);
							createdProducts.remove(productCode);
							break;
						default:
							System.out.println("Unknown operation " + operation + " for: " + productCode);
						}

					} catch (ApiRuntimeException ex) {

						ApiException apiEx = ex.getCause();
						if (apiEx.getCode() == 409) {
							createdProducts.add(productCode);
						} else {
							System.out.println("API error " + apiEx.getCode() + " on " + operation + " for product "+ productCode + ": " + apiEx.getMessage());
						}
					}
				}

				if (wrapper.getVariation() != null) {
					ProductVariation variation = wrapper.getVariation();
					String parentCode = wrapper.getParentProductCode();
					String variationKey = wrapper.getVariationKey();

					if (!createdProducts.contains(parentCode)) {
						System.out.println("Parent not created yet, skipping variation " + variationKey + "of: " + parentCode);
						continue;
					}
					try {
						retryService.updateVariation(parentCode, variationKey, variation);
					} catch (ApiRuntimeException ex) {
						ApiException apiEx = ex.getCause();
						System.out.println("API error " + apiEx.getCode() + " updating variation " + variationKey+ " of " + parentCode + ": " + apiEx.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println("Unexpected error in writer: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}