package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

@Component
public class CompanyAccountsDocumentDataManager {

    @Autowired
    private AccountsService accountsService;

    public <T> T getCompanyAccountData(CompanyAccounts companyAccounts, AccountType accountType,
                                       Transaction transaction, String resourceUri) throws ServiceException {

        String smallFullAccountLink = getCompanyAccountLink(companyAccounts, accountType);

        return (T) accountsService.getSmallFullAccounts(smallFullAccountLink, resourceUri, transaction);
    }

    private String getCompanyAccountLink(CompanyAccounts accounts, AccountType accountsType) {
        return accounts.getLinks().get(accountsType.getResourceKey());
    }
}
