package uk.gov.companieshouse.document.generator.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
public class DocumentGeneratorApplicationConfiguration {

    @Bean
    EnvironmentReader environmentReader() {
        return new EnvironmentReaderImpl();
    }
}
