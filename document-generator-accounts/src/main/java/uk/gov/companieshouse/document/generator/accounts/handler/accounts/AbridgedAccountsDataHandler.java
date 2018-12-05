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
import uk.gov.companieshouse.document.generator.accounts.LinkType;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.abridged.model.AbridgedAccountsApiData;
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
public class AbridgedAccountsDataHandler {

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
     * Get an Abridged accounts resource from the given resource link
     * @param resourceLink the resource link of the abridged accounts
     * @param requestId the id of the request
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    public DocumentInfoResponse getAbridgedAccountsData(String resourceLink, String requestId)
            throws HandlerException {

        Accounts accounts = getAccounts(resourceLink, requestId);

        //TODO update to get transaction link from accounts resource and obtain transaction - SFA-961
        Transaction transaction = null;

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
     * @param accounts accounts resource data
     * @return the {@link AccountType} that exist in the given accounts data
     * @throws HandlerException if unable to find account type in accounts data
     */
    private AccountType getAccountType(Accounts accounts) throws HandlerException {
        return accounts.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase(LinkType.SELF.getLink()))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" +
                        accounts.getId()));
    }

    private String getAccountLink(Accounts accounts, AccountType accountsType) {
        return accounts.getLinks().get(accountsType.getResourceKey());
    }

    private DocumentInfoResponse createResponse(Transaction transaction, AccountType accountType,
                                                AbridgedAccountsApi accountData)
            throws ServiceException, ParseException, IOException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createDocumentInfoResponseData(transaction, accountData));
        documentInfoResponse.setAssetId(accountType.getAssetId());
        documentInfoResponse.setTemplateName(accountType.getTemplateName());
        documentInfoResponse.setPath(createPathString(accountType));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", RESPONSE_DISPLAY_DATE_FORMAT
                .format(getCurrentPeriodEndOn(accountData)));

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

    private Date getCurrentPeriodEndOn(AbridgedAccountsApi accountData) throws ParseException {

        JSONObject account = new JSONObject(accountData);
        JSONObject currentPeriod = account.getJSONObject("currentPeriodApi");
        String periodEndOn = currentPeriod.get("periodEndDate").toString();
        return formatDate(periodEndOn);
    }

    private Date formatDate(String date) throws ParseException {
        return ISO_DATE_FORMAT.parse(date);
    }
}
