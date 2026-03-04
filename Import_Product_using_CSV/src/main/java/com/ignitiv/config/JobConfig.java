package com.ignitiv.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ignitiv.dto.ProductDTO;
import com.ignitiv.service.FileMoveListener;
import com.ignitiv.service.ProductWrapper;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;

@Configuration
@EnableBatchProcessing   
public class JobConfig {

	@Bean
	public Job job(JobRepository jobRepository, Step step, FileMoveListener listner) {
		return new JobBuilder("job", jobRepository).listener(listner).start(step).build();
	}
	
	@Bean
	public Step step(JobRepository jobRepository,
	                 PlatformTransactionManager transactionManager,
	                 ItemReader<ProductDTO> reader,
	                 ItemProcessor<ProductDTO, ProductWrapper> processor,
	                 ItemWriter<ProductWrapper> writer) {

	    return new StepBuilder("step", jobRepository)
	            .<ProductDTO, ProductWrapper>chunk(100, transactionManager)
	            .reader(reader)
	            .processor(processor)
	            .writer(writer)
	            .faultTolerant()
	            .retry(Exception.class)   
	            .retryLimit(3)
	            .build();
	}

}
