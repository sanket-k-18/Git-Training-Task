package com.ignitiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class KiboConfig {

    @Bean
	public KiboConfiguration getConfiguration() {
		KiboConfiguration config =  KiboConfiguration.builder()
				.withTenantId(41316)
				.withSiteId(63625)
				.withCredentials(
						ApiCredentials.builder().setClientId("gaurav_test_app")
						.setClientSecret("dc7ca79c0e854c82a5399b3add11a48f").build())
				.withTenantHost("t41316.sandbox.mozu.com")
				.withHomeHost("home.mozu.com")
				.build();
				
		return config;			
	}
}
