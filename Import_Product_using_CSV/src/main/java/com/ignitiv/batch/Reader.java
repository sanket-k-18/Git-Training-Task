package com.ignitiv.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ignitiv.dto.ProductDTO;
import com.ignitiv.util.Helpers;

@Configuration
public class Reader {
	
	@Autowired
	private Helpers helpers;
	
	@Bean("productReader")
	@StepScope
	public FlatFileItemReader<ProductDTO> productFileReader(@Value("#{jobParameters['filePath']}") String filePath){
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
					    "modelNumber",
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
	@Bean("synchronizedReader")
	@StepScope
	public SynchronizedItemStreamReader<ProductDTO> synchronizedReader(FlatFileItemReader<ProductDTO> productReader,  @Value("#{jobParameters['filePath']}") String filePath) {

	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String headerLine = br.readLine();
	        if (headerLine != null) {
	            String[] headers = headerLine.split(",");  
	            helpers.setOptionColumns(Arrays.asList(headers));
	        }
	        br.close();

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to read CSV headers", e);
	    }
	    return new SynchronizedItemStreamReaderBuilder<ProductDTO>()
	            .delegate(productReader)
	            .build();
	}
}
