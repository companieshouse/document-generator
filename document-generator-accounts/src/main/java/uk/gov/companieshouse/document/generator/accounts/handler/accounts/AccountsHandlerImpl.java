package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class AccountsHandlerImpl implements AccountsHandler  {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private CompanyService companyService;

    private static final DateFormat RESPONSE_DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy");

    private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String RESOURCE = "resource";

    private static final String ACCOUNT_TYPE = "accountType";

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentInfoResponse getAbridgedAccountsData(Transaction transaction, String resourceLink, String requestId)
            throws HandlerException {

        Accounts accounts = getAccounts(resourceLink, requestId);

        AccountType accountType = getAccountType(accounts);

        String abridgedAccountLink = getAccountLink(accounts, accountType);

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
        } catch (ParseException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, abridgedAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId,"Error when parsing period end on date from abridged accounts data", e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public DocumentInfoResponse getSmallFullAccountsData(Transaction transaction, String resourceLink, String requestId)
            throws HandlerException {

        Accounts accounts = getAccounts(resourceLink, requestId);

        AccountType accountType = getAccountType(accounts);

        String smallFullAccountLink = getAccountLink(accounts, accountType);

        try {
            SmallFullAccountIxbrl smallFullAccountIxbrl = accountsService.getSmallFullAccounts(smallFullAccountLink, resourceLink);
            return createResponse(transaction, accountType, smallFullAccountIxbrl);
        } catch (ServiceException | IOException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, smallFullAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId, "Error in service layer when obtaining smallFull accounts data for resource: "
                    + smallFullAccountLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        } catch (ParseException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, smallFullAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext(requestId,"Error when parsing period end on date from smallFull accounts data", e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }


    private Accounts getAccounts(String resourceLink, String requestId) throws HandlerException {

        Accounts accounts;

        try {
            accounts = accountsService.getAccounts(resourceLink, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, resourceLink);
            LOG.errorContext(requestId,"Error in service layer when obtaining accounts data for resource: "
                    + resourceLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        return accounts;
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
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" +
                        accountsData.getId()));
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
     * Creates the {@link DocumentInfoResponse} object
     * @param transaction transaction data
     * @param accountType account type
     * @param accountData The account data
     * @return {@link DocumentInfoResponse} object
     */
    private <T> DocumentInfoResponse createResponse(Transaction transaction, AccountType accountType,
                                                    T accountData) throws ParseException, IOException, ServiceException {
        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(transaction, accountData, accountType));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setPath(createPathString(accountType));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", RESPONSE_DISPLAY_DATE_FORMAT.format(getCurrentPeriodEndOn(accountData)));

        documentInfoResponse.setDescriptionValues(descriptionValues);
        documentInfoResponse.setDescriptionIdentifier(accountType.getEnumerationKey());
        return documentInfoResponse;
    }

    private String createPathString(AccountType accountType) {
        return String.format("/%s/%s", accountType.getAssetId(), accountType.getUniqueFileName());
    }

    /**
     * Creates the 'data' string in {@link DocumentInfoResponse}.
     * @param transaction the transaction data
     * @param accountData the accounts data
     * @param accountType the type of account
     * @return data string in {@link DocumentInfoResponse}
     */
    private <T> String createDocumentInfoResponseData(Transaction transaction, T accountData, AccountType accountType) throws IOException, ServiceException {
        String accountTypeName = accountType.getResourceKey();

        JacksonFactory factory = new JacksonFactory();

        JSONObject accountJSON = new JSONObject(factory.toString(accountData));
        JSONObject account = new JSONObject();

        account.put(accountTypeName, accountJSON);
        account.put("company_number", transaction.getCompanyNumber());

        CompanyProfileApi companyProfile = companyService.getCompanyProfile(transaction.getCompanyNumber());
        account.put("company_name", companyProfile.getCompanyName());

        return account.toString();
    }

    /**
     * Get the period end Date from the account data
     *
     * @param accountData the account data
     * @return periodEndDate The formatted periodEndDate
     * @throws ParseException
     */
    private <T> Date getCurrentPeriodEndOn(T accountData) throws ParseException {
        JSONObject account = new JSONObject(accountData);
        JSONObject currentPeriod = account.getJSONObject("currentPeriodApi");
        String periodEndOn = currentPeriod.get("periodEndDate").toString();

        return formatDate(periodEndOn);
    }

    /**
     * Convert into ISO 8601 date and time format
     *
     * @param date
     * @throws ParseException
     */
    private Date formatDate(String date) throws ParseException {
        return ISO_DATE_FORMAT.parse(date);
    }
}
