package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.IxbrlDataWrapper;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.CompanyAccountsDocumentDataManager;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.AccountsLinkNotFoundException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyAccountsDataHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CompanyAccountsDocumentDataManager companyAccountsDocumentDataManager;

    private static final String RESOURCE_URI = "resourceUri";

    private static final String ACCOUNT_TYPE = "accountType";

    private static final String TRANSACTION_LINK = "transactionLink";

    /**
     * Get a Company Accounts resource from the given resource link
     *
     * @param resourceUri the resource uri of the company accounts
     * @param requestId the id of the request
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    public DocumentInfoResponse getCompanyAccountsData(String resourceUri, String requestId)
            throws HandlerException {

        CompanyAccountsApi companyAccounts = getCompanyAccounts(resourceUri, requestId);

        String transactionLink = getTransactionLink(companyAccounts, resourceUri);

        Transaction transaction = getTransaction(transactionLink, requestId);

        AccountType accountType = getCompanyAccountType(companyAccounts);

        try {
            return createResponse(accountType, companyAccountsDocumentDataManager.getCompanyAccountDocumentData(
                    companyAccounts, accountType, transaction, requestId));
        } catch (ServiceException | IOException | AccountsLinkNotFoundException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE_URI, resourceUri);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId, "Error in service layer when obtaining accounts data for resource: "
                    + resourceUri, e, logMap);
            throw new HandlerException(e.getMessage(), e);
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

    private String getTransactionLink(CompanyAccountsApi companyAccounts, String resourceUri)
            throws HandlerException {

        if (companyAccounts.getLinks().getTransaction() != null) {
            return companyAccounts.getLinks().getTransaction();
        } else {
            throw new  HandlerException("Failed to get transaction link for resource Uri: "
                    + resourceUri);
        }
    }

    private AccountType getCompanyAccountType(CompanyAccountsApi companyAccounts) throws HandlerException {

        if (companyAccounts.getLinks().getSmallFullAccounts() != null) {
            return AccountType.getAccountType("small_full_accounts");
        } else {
            throw new HandlerException("Unable to find account type in account data" +
                    companyAccounts.getKind());
        }
    }

    private CompanyAccountsApi getCompanyAccounts(String resourceUri, String requestId) throws HandlerException {

        try {
            return accountsService.getCompanyAccounts(resourceUri, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE_URI, resourceUri);
            LOG.errorContext(requestId,"Error in service layer when obtaining company-accounts data for resource: "
                    + resourceUri, e, logMap);
            throw new HandlerException(e.getMessage(), e);
        }
    }

    private DocumentInfoResponse createResponse(AccountType accountType, IxbrlDataWrapper ixbrlDataWrapper)
            throws IOException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(ixbrlDataWrapper));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setPath(createPathString(accountType));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", getCurrentPeriodEndOn(ixbrlDataWrapper));

        documentInfoResponse.setDescriptionValues(descriptionValues);
        documentInfoResponse.setDescriptionIdentifier(accountType.getEnumerationKey());
        return documentInfoResponse;
    }

    private String createPathString(AccountType accountType) {
        return String.format("/%s/%s", accountType.getAssetId(), accountType.getUniqueFileName());
    }

    private String createDocumentInfoResponseData(IxbrlDataWrapper ixbrlDataWrapper) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(ixbrlDataWrapper);
    }

    private String getCurrentPeriodEndOn(IxbrlDataWrapper ixbrlDataWrapper) {
        return ixbrlDataWrapper.getAccounts().values().stream().findFirst().get().getPeriod().getCurrentPeriodEndsOn();
    }
}
