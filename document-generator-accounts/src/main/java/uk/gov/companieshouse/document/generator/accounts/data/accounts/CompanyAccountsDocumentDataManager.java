package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.ManagerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

@Component
public class CompanyAccountsDocumentDataManager {

    @Autowired
    private AccountsService accountsService;

    public <T> T getCompanyAccountDocumentData(CompanyAccounts companyAccounts, AccountType accountType,
                                       Transaction transaction, String resourceUri)
            throws ServiceException, ManagerException {

        String smallFullAccountLink = getCompanyAccountLink(companyAccounts, accountType);

        return (T) accountsService.getSmallFullAccounts(smallFullAccountLink, resourceUri, transaction);
    }

    private String getCompanyAccountLink(CompanyAccounts accounts, AccountType accountsType) throws ManagerException {

        switch(accountsType.getResourceKey()) {
            case "small_full_accounts":
                if (accounts.getLinks().getSmallFullAccounts() != null) {
                    return accounts.getLinks().getSmallFullAccounts();
                } else {
                    throw new ManagerException("No small full accounts link was located in the company accounts link for" +
                            " account type: " + accountsType.getResourceKey());
                }
            default:
                throw new ManagerException("No company account link was located for account type: "
                        + accountsType.getResourceKey());
        }

    }
}

