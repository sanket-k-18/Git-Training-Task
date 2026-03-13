package com.ignitiv.dto;

import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private String operations;

	private String productCode;

	private String parentProductCode;

	private String productName;

	private String productUsage;
	
	private Integer productTypeId;

	private String productType;

	private Integer catalogId;

	private Integer masterCatalogId;

	private String shortDescription;

	private String longDescription;

	private Double price;

	private Double salePrice;

	private Double msrp;

	private String colorOptions;

	private String sizeOptions;
	
	private String materialOptions;

	private String brand;
	
	private String modelNumber;

	private String giftWrap;

	private Boolean manageStock;

	private String outOfStockBehavior;

	private String packageHeightUnit;

	private Double packageHeightValue;

	private String packageWidthUnit;

	private Double packageWidthValue;

	private String packageLengthUnit;

	private Double packageLengthValue;

	private String packageWeightUnit;

	private Double packageWeightValue;

	private Boolean isTaxable;

	private Boolean isActive;

	private String upc;
	
	
	
	
	public String getFieldValue(String fieldName) {

	    try {

	        Field field = this.getClass().getDeclaredField(fieldName);
	        field.setAccessible(true);
	        Object value = field.get(this);
	        return value == null ? null : value.toString();

	    } catch (Exception e) {
	        return null;
	    }
	}




	@Override
	public String toString() {
		return "ProductDTO [operations=" + operations + ", productCode=" + productCode + ", parentProductCode="
				+ parentProductCode + ", productName=" + productName + ", productUsage=" + productUsage
				+ ", productTypeId=" + productTypeId + ", productType=" + productType + ", catalogId=" + catalogId
				+ ", masterCatalogId=" + masterCatalogId + ", shortDescription=" + shortDescription
				+ ", longDescription=" + longDescription + ", price=" + price + ", salePrice=" + salePrice + ", msrp="
				+ msrp + ", colorOptions=" + colorOptions + ", sizeOptions=" + sizeOptions + ", materialOptions="
				+ materialOptions + ", brand=" + brand + ", modelNumber=" + modelNumber + ", giftWrap=" + giftWrap
				+ ", manageStock=" + manageStock + ", outOfStockBehavior=" + outOfStockBehavior + ", packageHeightUnit="
				+ packageHeightUnit + ", packageHeightValue=" + packageHeightValue + ", packageWidthUnit="
				+ packageWidthUnit + ", packageWidthValue=" + packageWidthValue + ", packageLengthUnit="
				+ packageLengthUnit + ", packageLengthValue=" + packageLengthValue + ", packageWeightUnit="
				+ packageWeightUnit + ", packageWeightValue=" + packageWeightValue + ", isTaxable=" + isTaxable
				+ ", isActive=" + isActive + ", upc=" + upc + ", getOperations()=" + getOperations()
				+ ", getProductCode()=" + getProductCode() + ", getParentProductCode()=" + getParentProductCode()
				+ ", getProductName()=" + getProductName() + ", getProductUsage()=" + getProductUsage()
				+ ", getProductTypeId()=" + getProductTypeId() + ", getProductType()=" + getProductType()
				+ ", getCatalogId()=" + getCatalogId() + ", getMasterCatalogId()=" + getMasterCatalogId()
				+ ", getShortDescription()=" + getShortDescription() + ", getLongDescription()=" + getLongDescription()
				+ ", getPrice()=" + getPrice() + ", getSalePrice()=" + getSalePrice() + ", getMsrp()=" + getMsrp()
				+ ", getColorOptions()=" + getColorOptions() + ", getSizeOptions()=" + getSizeOptions()
				+ ", getMaterialOptions()=" + getMaterialOptions() + ", getBrand()=" + getBrand()
				+ ", getModelNumber()=" + getModelNumber() + ", getGiftWrap()=" + getGiftWrap() + ", getManageStock()="
				+ getManageStock() + ", getOutOfStockBehavior()=" + getOutOfStockBehavior()
				+ ", getPackageHeightUnit()=" + getPackageHeightUnit() + ", getPackageHeightValue()="
				+ getPackageHeightValue() + ", getPackageWidthUnit()=" + getPackageWidthUnit()
				+ ", getPackageWidthValue()=" + getPackageWidthValue() + ", getPackageLengthUnit()="
				+ getPackageLengthUnit() + ", getPackageLengthValue()=" + getPackageLengthValue()
				+ ", getPackageWeightUnit()=" + getPackageWeightUnit() + ", getPackageWeightValue()="
				+ getPackageWeightValue() + ", getIsTaxable()=" + getIsTaxable() + ", getIsActive()=" + getIsActive()
				+ ", getUpc()=" + getUpc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}