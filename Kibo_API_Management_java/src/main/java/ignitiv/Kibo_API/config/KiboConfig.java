package ignitiv.Kibo_API.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import com.kibocommerce.sdk.common.ApiCredentials;
import com.kibocommerce.sdk.common.KiboConfiguration;

@Configuration
public class KiboConfig {
	
	 @Bean
		public KiboConfiguration getConfiguration() {
			KiboConfiguration config =  KiboConfiguration.builder()
					.withTenantId(52670)
					.withSiteId(77583)
					.withCredentials(
							ApiCredentials.builder().setClientId("1908")
							.setClientSecret("db56ada674e74352aa5154b0de3f745d").build())
					.withTenantHost("t52670.sandbox.mozu.com")
					.withHomeHost("home.mozu.com")
					.build();
					
			return config;
					
		}

}
