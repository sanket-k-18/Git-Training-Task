package com.ignitiv.config;

import org.springframework.context.annotation.Configuration;

import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class KiboConfig {
	
	public KiboConfiguration getConfiguration() {
		return KiboConfiguration.builder()
				.withTenantId(52898)
				.withSiteId(77852)
				.withCredentials(
						ApiCredentials.builder().setClientId("gaurav_test_app")
						.setClientSecret("dc7ca79c0e854c82a5399b3add11a48f").build())
				.withTenantHost("t52898.sandbox.mozu.com")
				.withHomeHost("home.mozu.com")
				.build();
	}
}
