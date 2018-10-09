package uk.gov.companieshouse.document.generator.api;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl;
import uk.gov.companieshouse.document.generator.api.factory.DocumentInfoServiceFactory;
import uk.gov.companieshouse.document.generator.sjp.SjpDocumentInfoServiceImpl;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
@ComponentScan(basePackages = {"uk.gov.companieshouse.document.generator.accounts", "uk.gov.companieshouse.document.generator.sjp"})
public class DocumentGeneratorApplicationConfiguration {

    @Bean
    public FactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(DocumentInfoServiceFactory.class);
        return factoryBean;
    }

    @Bean(name = "ACCOUNTS")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AccountsDocumentInfoServiceImpl accountsDocumentInfoService() {
        return new AccountsDocumentInfoServiceImpl();
    }

    @Bean(name = "SJP")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SjpDocumentInfoServiceImpl sjpDocumentInfoService() {
        return new SjpDocumentInfoServiceImpl();
    }

    @Bean
    EnvironmentReader environmentReader() {
        return new EnvironmentReaderImpl();
    }
}
