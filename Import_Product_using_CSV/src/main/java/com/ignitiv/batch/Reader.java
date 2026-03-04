package com.ignitiv.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ignitiv.dto.ProductDTO;

@Configuration
public class Reader {
	
	@Bean("productReader")
	@StepScope
	public FlatFileItemReader<ProductDTO> reader(@Value("#{jobParameters['filePath']}") String filePath){
		return new FlatFileItemReaderBuilder<ProductDTO>()
				.name("productReader")
				.resource(new FileSystemResource(filePath))
				.delimited()
				.names(
					    "operations",
					    "productCode",
					    "parentProductCode",
					    "productName",
					    "productTypeId",
					    "productUsage",
					    "productType",
					    "catalogId",
					    "masterCatalogId",
					    "shortDescription",
					    "longDescription",
					    "price",
					    "salePrice",
					    "msrp",
					    "colorOptions",
					    "materialOptions",
					    "sizeOptions",
					    "brand",
					    "giftWrap",                
					    "manageStock",
					    "outOfStockBehavior",
					    "packageHeightUnit",       
					    "packageHeightValue",      
					    "packageWidthUnit",        
					    "packageWidthValue",      
					    "packageLengthUnit",       
					    "packageLengthValue",      
					    "packageWeightUnit",       
					    "packageWeightValue",      
					    "isTaxable",
					    "isActive",
					    "upc"
					)
				.targetType(ProductDTO.class)
				.linesToSkip(1)
				.strict(false)
				.build();
	}
}
