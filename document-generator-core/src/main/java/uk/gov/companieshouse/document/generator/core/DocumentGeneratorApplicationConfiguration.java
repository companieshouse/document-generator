package uk.gov.companieshouse.document.generator.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandler;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandlerImpl;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.accounts.service.impl.TransactionServiceImpl;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
public class DocumentGeneratorApplicationConfiguration {

    @Bean
    EnvironmentReader environmentReader() {
        return new EnvironmentReaderImpl();
    }

    @Bean
    DocumentInfoService documentInfoService() {
        return new AccountsDocumentInfoServiceImpl();
    }

    @Bean
    TransactionService transactionService() {
        return new TransactionServiceImpl();
    }

    @Bean
    AccountsHandler accountsHandler() {
        return new AccountsHandlerImpl();
    }
}
