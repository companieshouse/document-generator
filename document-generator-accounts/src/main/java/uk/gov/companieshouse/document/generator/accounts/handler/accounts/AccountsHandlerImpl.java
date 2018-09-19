package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class AccountsHandlerImpl implements AccountsHandler  {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsService accountsService;

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();
    private final String bucketName = READER.getOptionalString("DOC_GEN_ACC_BUCKET_NAME");

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentInfoResponse getAbridgedAccountsData(Transaction transaction, String resourceLink) throws HandlerException {
        Accounts accounts;

        try {
            accounts = accountsService.getAccounts(resourceLink);
        } catch (ServiceException e) {
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        AccountType accountType = getAccountType(accounts);

        String abridgedAccountLink = getAccountLink(accounts, accountType);
        try {
            AbridgedAccountsApi abridgedAccountData = accountsService.getAbridgedAccounts(abridgedAccountLink);

            return createResponse(transaction, accountType, abridgedAccountData);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("resource", abridgedAccountLink);
            logMap.put("accountType", accountType);
            LOG.error("Error in service layer", logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }


    /**
     * Get the account type from the links resource within the given accounts data object
     *
     * @param accountsData accounts resource data
     * @return the {@link AccountType} that exist in the given accounts data
     * @throws HandlerException if unable to find account type in accounts data
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

    /**
     * Creates the 'data' string in {@link DocumentInfoResponse}.
     * @param transaction the transaction data
     * @param abridgedAccountData the abridged accounts data
     * @return data string in {@link DocumentInfoResponse}
     */
    private String createDocumentInfoResponseData(Transaction transaction, AbridgedAccountsApi abridgedAccountData) {
        JSONObject abridgedAccountJSON = new JSONObject(abridgedAccountData);
        JSONObject abridgedAccount = new JSONObject();
        abridgedAccount.put("abridged_account", abridgedAccountJSON);
        abridgedAccount.put("company_name", transaction.getCompanyName());
        abridgedAccount.put("company_number", transaction.getCompanyNumber());
        return abridgedAccount.toString();
    }

    /**
     * Creates the {@link DocumentInfoResponse} object
     * @param transaction transaction data
     * @param accountType account type
     * @param abridgedAccountData abridged account data
     * @return {@link DocumentInfoResponse} object
     */
    private DocumentInfoResponse createResponse(Transaction transaction, AccountType accountType, AbridgedAccountsApi abridgedAccountData) {
        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(transaction, abridgedAccountData));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setLocation(createLocationString(accountType));
        return documentInfoResponse;
    }

    private String createLocationString(AccountType accountType) {
        return String.format("%s/%s/%s", getBucketName(), accountType.getAssetId(), accountType.getUniqueFileName());
    }

    private String getBucketName() {
        return bucketName;
    }
}
