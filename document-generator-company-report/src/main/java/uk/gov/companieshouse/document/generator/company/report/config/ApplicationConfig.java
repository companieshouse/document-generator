package uk.gov.companieshouse.document.generator.company.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    /**
     * @return restTemplate - which is needed in the accounts module (that imports this module)
     */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
