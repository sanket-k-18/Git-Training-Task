package com.ignitiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class KiboConfig {

    @Bean
	public KiboConfiguration getConfiguration() {
		KiboConfiguration config =  KiboConfiguration.builder()
				.withTenantId(52898)
				.withSiteId(77852)
				.withCredentials(
						ApiCredentials.builder().setClientId("gaurav_test_app")
						.setClientSecret("dc7ca79c0e854c82a5399b3add11a48f").build())
				.withTenantHost("t52898.sandbox.mozu.com")
				.withHomeHost("home.mozu.com")
				.build();
				
		return config;			
	}
    
    
    @Bean
    public ObjectMapper mapper() {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.findAndRegisterModules();            //Detect and register all jackson modules 
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //to format dates from this "createdAt": 1704776400000 to this "createdAt": "2026-01-09T10:30:00Z"
        return mapper;
    	
    }
}
