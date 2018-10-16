package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
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

    private static final DateFormat RESPONSE_DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy");

    private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String RESOURCE = "resource";

    private static final String ACCOUNT_TYPE = "accountType";

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentInfoResponse getAbridgedAccountsData(Transaction transaction, String resourceLink)
            throws HandlerException {
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
        } catch (ServiceException | IOException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, abridgedAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.error("Error in service layer", logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        } catch (ParseException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, abridgedAccountLink);
            logMap.put(ACCOUNT_TYPE, accountType);
            LOG.errorContext("Error when parsing period end on date from abridged accounts data", e, logMap);
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
                                                    T accountData) throws ParseException, IOException {
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
    private <T> String createDocumentInfoResponseData(Transaction transaction, T accountData, AccountType accountType) throws IOException {
        String accountTypeName = accountType.getResourceKey();

        JacksonFactory factory = new JacksonFactory();

        JSONObject accountJSON = new JSONObject(factory.toString(accountData));
        JSONObject account = new JSONObject();
        account.put(accountTypeName, accountJSON);
        account.put("company_number", transaction.getCompanyNumber());

        //TODO - Currently hardcoded for testing purposes, will return to grab the correct company name
        account.put("company_name", "THE GIRLS' DAY SCHOOL TRUST");

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
