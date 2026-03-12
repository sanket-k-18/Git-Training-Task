package com.ignitiv.config;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.ignitiv.batch.JobListener;
import com.ignitiv.batch.ParentProcessor;
import com.ignitiv.batch.VariationProcessor;
import com.ignitiv.dto.ProductDTO;
import com.ignitiv.service.FileMoveListener;
import com.ignitiv.service.ProductWrapper;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Bean
    public Job job(JobRepository jobRepository,
                   Step parentStep,
                   Step variationStep,
                   FileMoveListener fileMoveListener,
                   JobListener jobListener) {

        return new JobBuilder("job", jobRepository)
                .listener(jobListener)
                .listener(fileMoveListener)
                .start(parentStep)
                .next(variationStep)
                .build();
    }

    @Bean
    public Step parentStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           @Qualifier("synchronizedReader") ItemReader<ProductDTO> reader,
                           ParentProcessor processor,
                           ItemWriter<ProductWrapper> writer,
                           TaskExecutor taskExecutor) {

        return new StepBuilder("parentStep", jobRepository)
                .<ProductDTO, ProductWrapper>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step variationStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              @Qualifier("synchronizedReader") ItemReader<ProductDTO> reader,
                              VariationProcessor processor,
                              ItemWriter<ProductWrapper> writer,
                              TaskExecutor taskExecutor) {

        return new StepBuilder("variationStep", jobRepository)
                .<ProductDTO, ProductWrapper>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }
}