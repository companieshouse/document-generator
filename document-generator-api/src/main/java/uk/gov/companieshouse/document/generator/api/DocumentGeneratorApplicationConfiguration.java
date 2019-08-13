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
import uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
@ComponentScan(basePackages = {"uk.gov.companieshouse.document.generator.accounts",
                               "uk.gov.companieshouse.document.generator.prosecution",
                               "uk.gov.companieshouse.document.generator.company.report",
                               "uk.gov.companieshouse.document.generator.common" })

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

    @Bean(name = "PROSECUTION")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProsecutionDocumentInfoService prosecutionDocumentInfoService() {
        return new ProsecutionDocumentInfoService();
    }

    @Bean(name = "COMPANY_REPORT")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CompanyReportDocumentInfoServiceImpl companyReportDocumentInfoService() {
        return new CompanyReportDocumentInfoServiceImpl();
    }

    @Bean
    EnvironmentReader environmentReader() {
        return new EnvironmentReaderImpl();
    }
}
