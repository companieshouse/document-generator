package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentInfoResponse getAbridgedAccountsData(String resourceLink) throws HandlerException {
        Accounts accounts;

        try {
            accounts = accountsService.getAccounts(resourceLink);
        } catch (ServiceException e) {
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        AccountType accountsType = getAccountType(accounts);

        String abridgedAccountLink = getAccountLink(accounts, accountsType);
        try {
            // TODO: abridgedAccountData will be used as part of the implementation of converting
            // TODO: the data object to a json string
            AbridgedAccountsApi abridgedAccountData = accountsService.getAbridgedAccounts(abridgedAccountLink);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("resource", abridgedAccountLink);
            logMap.put("accountType", accountsType);
            LOG.error("Error in service layer", logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        return new DocumentInfoResponse();
    }


    /**
     * Get the account type from the links resource within the given accounts data object
     *
     * @param accountsData accounts resource data
     * @return the {@link AccountType} that exist in the given accounts data
     * @throws {@link HandlerException} if unable to find account type in accounts data
     */
    private AccountType getAccountType(Accounts accountsData) throws HandlerException {
        return accountsData.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase(LinkType.SELF.getLink()))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" + accountsData.getId()));
    }

    /**
     * Gets the link in the given accounts data for the given account type
     *
     * @param accounts accounts resource data
     * @param accountsType {@link AccountType}
     * @return link of given accounts type in given accounts object
     */
    private String getAccountLink(Accounts accounts, AccountType accountsType) {
        return accounts.getLinks().get(accountsType.getResourceKey());
    }
    
}
