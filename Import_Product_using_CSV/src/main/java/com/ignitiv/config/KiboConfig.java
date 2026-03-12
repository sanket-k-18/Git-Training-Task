package com.ignitiv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class KiboConfig {
	
	
	@Value("${kibo.clientId}")
    private String clientId;

    @Value("${kibo.clientSecret}")
    private String clientSecret;

    @Value("${kibo.tenantId}")
    private Integer tenantId;

    @Value("${kibo.siteId}")
    private Integer siteId;

    @Value("${kibo.tenantHost}")
    private String tenantHost;

    @Value("${kibo.homeHost}")
    private String homeHost;
    
    @Bean
	public KiboConfiguration getConfiguration() {
		return KiboConfiguration.builder()
				.withTenantId(tenantId)
				.withSiteId(siteId)
				.withCredentials(
							ApiCredentials.builder().setClientId(clientId)
						.setClientSecret(clientSecret).build())
				.withTenantHost(tenantHost)
				.withHomeHost(homeHost)
				.build();
	}
}
