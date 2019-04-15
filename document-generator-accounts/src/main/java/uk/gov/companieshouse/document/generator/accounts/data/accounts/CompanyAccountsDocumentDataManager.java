package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.AccountsLinkNotFoundException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

@Component
public class CompanyAccountsDocumentDataManager {

    @Autowired
    private AccountsService accountsService;

    public <T> T getCompanyAccountDocumentData(CompanyAccountsApi companyAccounts, AccountType accountType,
                                               Transaction transaction, String requestId)
            throws ServiceException, AccountsLinkNotFoundException {

        String smallFullAccountLink = getCompanyAccountLink(companyAccounts, accountType);

        return (T) accountsService.getSmallFullAccounts(smallFullAccountLink, requestId, transaction);
    }

    private String getCompanyAccountLink(CompanyAccountsApi accounts, AccountType accountsType) throws AccountsLinkNotFoundException {

        switch(accountsType.getResourceKey()) {
            case "small_full_accounts":
                if (accounts.getLinks().getSmallFullAccounts() != null) {
                    return accounts.getLinks().getSmallFullAccounts();
                } else {
                    throw new AccountsLinkNotFoundException("No small full accounts link was located in the company accounts link for" +
                            " account type: " + accountsType.getResourceKey());
                }
            default:
                throw new AccountsLinkNotFoundException("No company account link was located for account type: "
                        + accountsType.getResourceKey());
        }

    }
}

