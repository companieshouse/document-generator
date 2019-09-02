package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsLinks;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.IxbrlDataWrapper;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.AccountsLinkNotFoundException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.CicReportService;

@Component
public class CompanyAccountsDocumentDataManager {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private CicReportService cicReportService;

    public IxbrlDataWrapper getCompanyAccountDocumentData(CompanyAccountsApi companyAccounts, AccountType accountType,
                                               Transaction transaction, String requestId)
            throws ServiceException, AccountsLinkNotFoundException {

        IxbrlDataWrapper ixbrlDataWrapper = new IxbrlDataWrapper();

        ixbrlDataWrapper.setAccounts(
                Collections.singletonMap(
                        accountType.getResourceKey(),
                                accountsService.getSmallFullAccounts(
                                        getCompanyAccountLink(companyAccounts, accountType), requestId, transaction)));

        Optional<String> cicReportLink = getCicReportLink(companyAccounts);
        if (cicReportLink.isPresent()) {
            ixbrlDataWrapper
                    .setCicReport(cicReportService.getCicReport(cicReportLink.get(), requestId));
        }

        return ixbrlDataWrapper;
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

    private Optional<String> getCicReportLink(CompanyAccountsApi accounts) {

        return Optional.ofNullable(accounts)
                        .map(CompanyAccountsApi::getLinks)
                        .map(CompanyAccountsLinks::getCicReport);
    }
}

