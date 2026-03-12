package com.ignitiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class ImportProductUsingCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportProductUsingCsvApplication.class, args);
	}

}
