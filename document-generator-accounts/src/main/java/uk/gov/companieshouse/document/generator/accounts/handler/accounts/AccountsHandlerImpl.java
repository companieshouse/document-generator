package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class AccountsHandlerImpl implements AccountsHandler  {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    AccountsService accountsService;

    @Override
    public DocumentInfoResponse getAccountsData(String resourceLink) throws HandlerException {
        Accounts accounts;

        try {
            accounts = accountsService.getAccounts(resourceLink);
        } catch (ServiceException e) {
            LOG.error(e);
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        // TODO: The accountsType variable shall be used in the implementation of abridged logic
        AccountType accountsType = getAccountType(accounts);

        return new DocumentInfoResponse();
    }


    /**
     * Get the account type from the account link within links
     *
     * @return accountsData - accounts data
     */
    private AccountType getAccountType(Accounts accountsData) throws HandlerException {
        return accountsData.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase(LinkType.SELF.getLink()))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" + accountsData.getId()));
    }
}
