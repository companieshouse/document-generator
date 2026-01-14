package uk.gov.companieshouse.document.generator.company.report.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.environment.EnvironmentReader;

@Configuration
public class ApplicationConfig {

    private static final String API_URL = "API_URL";

    @Autowired
    EnvironmentReader environmentReader;

    /**
     * @return restTemplate - which is needed in the accounts module (that imports this module)
     */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

    @Bean
    public RestClient getRestClient() { return RestClient.builder().baseUrl((environmentReader.getMandatoryString(API_URL))).build(); }
}
