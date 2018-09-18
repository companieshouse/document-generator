package uk.gov.companieshouse.document.generator.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
@ComponentScan(basePackages = {"uk.gov.companieshouse.document.generator.accounts"})
public class DocumentGeneratorApplicationConfiguration {

    @Bean
    EnvironmentReader environmentReader() {
        return new EnvironmentReaderImpl();
    }

    @Bean
    DocumentInfoService documentInfoService() {
        return new AccountsDocumentInfoServiceImpl();
    }
}
