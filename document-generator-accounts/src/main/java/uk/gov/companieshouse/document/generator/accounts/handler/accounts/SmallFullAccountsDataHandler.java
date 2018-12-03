package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class SmallFullAccountsDataHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TransactionService transactionService;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    private static final String RESOURCE = "resource";

    private static final String ACCOUNT_TYPE = "accountType";

    private static final String TRANSACTION_ID = "transactionId";

    /**
     * Get a SmallFull Accounts resource from the given resource link
     *
     * @param resourceUri the resource uri of the smallFull accounts
     * @param requestId the id of the request
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    public DocumentInfoResponse getSmallFullAccountsData(String resourceUri, String requestId)
            throws HandlerException {

        CompanyAccounts companyAccounts = getCompanyAccounts(resourceUri, requestId);

        Transaction transaction = getTransaction(companyAccounts, requestId, resourceUri);

        AccountType accountType = getCompanyAccountType(companyAccounts);

        String smallFullAccountLink = getCompanyAccountLink(companyAccounts, accountType);

        try {
            SmallFullAccountIxbrl smallFullAccountIxbrl = accountsService.getSmallFullAccounts(smallFullAccountLink,
                    resourceUri, transaction);
            return createResponse(accountType, smallFullAccountIxbrl);
        } catch (ServiceException | IOException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, smallFullAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId, "Error in service layer when obtaining smallFull accounts data for resource: "
                    + smallFullAccountLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }

    private Transaction getTransaction(CompanyAccounts companyAccounts, String requestId, String resourceId)
            throws HandlerException {

        String transactionId = getTransactionId(companyAccounts, requestId, resourceId);
        try {
            return transactionService.getTransaction(transactionId, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(TRANSACTION_ID, transactionId);
            LOG.errorContext(requestId,"An error occurred when calling the transaction service with resource id: "
                    + transactionId, e, logMap);
            throw new HandlerException("Failed to get transaction with resourceId: " + transactionId, e);
        }
    }

    private String getTransactionId(CompanyAccounts companyAccounts, String requestId, String resourceUri) {

        return companyAccounts.getLinks().entrySet()
                .stream()
                .filter(map -> map.getKey().equals("transaction"))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    Map <String, Object> debugMap = new HashMap <>();
                    debugMap.put(RESOURCE, resourceUri);
                    LOG.infoContext(requestId, "No transaction was found in the company accounts resource for resource Uri: "
                    + resourceUri, debugMap);

            return null;
        });
    }

    private AccountType getCompanyAccountType(CompanyAccounts accountsData) throws HandlerException {
        return accountsData.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase(LinkType.SELF.getLink()))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" +
                        accountsData.getKind()));
    }

    private CompanyAccounts getCompanyAccounts(String resourceUri, String requestId) throws HandlerException {

        CompanyAccounts accounts;

        try {
            accounts = accountsService.getCompanyAccounts(resourceUri, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, resourceUri);
            LOG.errorContext(requestId,"Error in service layer when obtaining company-accounts data for resource: "
                    + resourceUri, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        return accounts;
    }

    private String getCompanyAccountLink(CompanyAccounts accounts, AccountType accountsType) {
        return accounts.getLinks().get(accountsType.getResourceKey());
    }

    private DocumentInfoResponse createResponse(AccountType accountType, SmallFullAccountIxbrl accountData)
            throws IOException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(accountData));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setPath(createPathString(accountType));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", accountsDatesHelper
                .convertDateToString(getCurrentPeriodEndOn(accountData)));

        documentInfoResponse.setDescriptionValues(descriptionValues);
        documentInfoResponse.setDescriptionIdentifier(accountType.getEnumerationKey());
        return documentInfoResponse;
    }

    private String createPathString(AccountType accountType) {
        return String.format("/%s/%s", accountType.getAssetId(), accountType.getUniqueFileName());
    }

    private String createDocumentInfoResponseData(SmallFullAccountIxbrl accountData) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(accountData);
    }

    private LocalDate getCurrentPeriodEndOn(SmallFullAccountIxbrl accountData) {
        return formatDate(accountData.getPeriod().getCurrentPeriodEndsOn());
    }

    private LocalDate formatDate(String date) {
        return accountsDatesHelper.convertStringToDate(date);
    }
}
