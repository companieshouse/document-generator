package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.abridged.model.AbridgedAccountsApiData;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class AbridgedAccountsDataHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TransactionService transactionService;

    private static final String RESOURCE = "resource";

    private static final String ACCOUNT_TYPE = "accountType";

    private static final String TRANSACTION_LINK = "transactionLink";

    /**
     * Get an Abridged accounts resource from the given resource link
     * @param resourceUri the resource uri of the abridged accounts
     * @param requestId the id of the request
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    public DocumentInfoResponse getAbridgedAccountsData(String resourceUri, String requestId)
            throws HandlerException {

        Accounts accounts = getAccounts(resourceUri, requestId);

        String transactionLink = getTransactionLink(accounts, resourceUri);

        Transaction transaction = getTransaction(transactionLink, requestId);

        AccountType accountType = getAccountType(accounts);

        String abridgedAccountLink = getAccountLink(accounts);

        try {
            AbridgedAccountsApi abridgedAccountData = accountsService.getAbridgedAccounts(abridgedAccountLink, requestId);
            return createResponse(transaction, accountType, abridgedAccountData);
        } catch (ServiceException | IOException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, abridgedAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId,"Error in service layer when obtaining abridged accounts data for resource: "
                    + abridgedAccountLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }

    private String getTransactionLink(Accounts accounts, String resourceUri)
            throws HandlerException {

        if (accounts.getLinks().getTransaction() != null) {
            return accounts.getLinks().getTransaction();
        } else {
            throw new  HandlerException("Failed to get transaction link for resource Uri: "
                    + resourceUri);
        }
    }

    private Transaction getTransaction(String transactionLink, String requestId)
            throws HandlerException {

        try {
            return transactionService.getTransaction(transactionLink, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(TRANSACTION_LINK, transactionLink);
            LOG.errorContext(requestId,"An error occurred when calling the transaction service with transaction link: "
                    + transactionLink, e, logMap);
            throw new HandlerException("Failed to get transaction with transaction link: " + transactionLink, e);
        }
    }

    private Accounts getAccounts(String resourceLink, String requestId) throws HandlerException {

        try {
             return accountsService.getAccounts(resourceLink, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, resourceLink);
            LOG.errorContext(requestId,"Error in service layer when obtaining accounts data for resource: "
                    + resourceLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }

    private AccountType getAccountType(Accounts accounts) throws HandlerException {

        if (accounts.getLinks().getAbridgedAccounts() != null) {
            return AccountType.getAccountType("abridged_accounts");
        } else {
            throw new HandlerException("Unable to find account type in account data: " +
                    accounts.getKind());
        }
    }

    private String getAccountLink(Accounts accounts) {
        return accounts.getLinks().getAbridgedAccounts();
    }

    private DocumentInfoResponse createResponse(Transaction transaction, AccountType accountType,
                                                AbridgedAccountsApi accountData)
            throws ServiceException, IOException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(transaction, accountData));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setPath(createPathString(accountType));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", getCurrentPeriodEndOn(accountData));

        documentInfoResponse.setDescriptionValues(descriptionValues);
        documentInfoResponse.setDescriptionIdentifier(accountType.getEnumerationKey());
        return documentInfoResponse;
    }

    private String createPathString(AccountType accountType) {
        return String.format("/%s/%s", accountType.getAssetId(), accountType.getUniqueFileName());
    }

    private String createDocumentInfoResponseData(Transaction transaction, AbridgedAccountsApi accountData)
            throws ServiceException, IOException {

        AbridgedAccountsApiData abridgedAccountsApiData = new AbridgedAccountsApiData();
        abridgedAccountsApiData.setAbridgedAccountsApi(accountData);

        CompanyProfileApi companyProfile = companyService.getCompanyProfile(transaction.getCompanyNumber());
        abridgedAccountsApiData.setCompanyName(companyProfile.getCompanyName());
        abridgedAccountsApiData.setCompanyNumber(companyProfile.getCompanyNumber());

        return writeAccountsValues(abridgedAccountsApiData);
    }

    private String writeAccountsValues(AbridgedAccountsApiData abridgedAccountsApiData) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String accountsJSON = mapper.writeValueAsString(abridgedAccountsApiData);
        JsonNode accounts = mapper.readTree(accountsJSON);

        return accounts.toString();
    }

    private String getCurrentPeriodEndOn(AbridgedAccountsApi accountData) {

        JSONObject account = new JSONObject(accountData);
        JSONObject currentPeriod = account.getJSONObject("currentPeriodApi");
        String periodEndOn = currentPeriod.get("periodEndDate").toString();
        return periodEndOn;
    }
}
