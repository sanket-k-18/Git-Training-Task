package com.ignitiv.dto;

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
	
	
	@Override
	public String toString() {
		return "ProductDTO [operations=" + operations + ", productCode=" + productCode + ", parentProductCode="
				+ parentProductCode + ", productName=" + productName + ", productUsage=" + productUsage
				+ ", productTypeId=" + productTypeId + ", productType=" + productType + ", catalogId=" + catalogId
				+ ", masterCatalogId=" + masterCatalogId + ", shortDescription=" + shortDescription
				+ ", longDescription=" + longDescription + ", price=" + price + ", salePrice=" + salePrice + ", msrp="
				+ msrp + ", colorOptions=" + colorOptions + ", sizeOptions=" + sizeOptions + ", brand=" + brand
				+ ", giftWrap=" + giftWrap + ", manageStock=" + manageStock + ", outOfStockBehavior="
				+ outOfStockBehavior + ", packageHeightUnit=" + packageHeightUnit + ", packageHeightValue="
				+ packageHeightValue + ", packageWidthUnit=" + packageWidthUnit + ", packageWidthValue="
				+ packageWidthValue + ", packageLengthUnit=" + packageLengthUnit + ", packageLengthValue="
				+ packageLengthValue + ", packageWeightUnit=" + packageWeightUnit + ", packageWeightValue="
				+ packageWeightValue + ", isTaxable=" + isTaxable + ", isActive=" + isActive + ", upc=" + upc + "]";
	}
}