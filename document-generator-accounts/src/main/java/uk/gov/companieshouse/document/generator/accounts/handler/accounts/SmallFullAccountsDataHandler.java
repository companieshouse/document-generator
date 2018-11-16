package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private DateTimeFormatter DATE_TIME_FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeFormatter DATE_TIME_FORMATTER_RESPONSE_DISPLAY = DateTimeFormatter.ofPattern("dd MMMMM yyyy");

    private static final String RESOURCE = "resource";

    private static final String ACCOUNT_TYPE = "accountType";

    /**
     * Get a SmallFull Accounts resource from the given resource link
     *
     * @param transaction the transaction data
     * @param resourceLink the resource link of the smallFull accounts
     * @param requestId the id of the request
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    public DocumentInfoResponse getSmallFullAccountsData(Transaction transaction, String resourceLink, String requestId)
            throws HandlerException {

        CompanyAccounts accounts = getCompanyAccounts(resourceLink, requestId);

        AccountType accountType = getCompanyAccountType(accounts);

        String smallFullAccountLink = getCompanyAccountLink(accounts, accountType);

        try {
            SmallFullAccountIxbrl smallFullAccountIxbrl = accountsService.getSmallFullAccounts(smallFullAccountLink, resourceLink, transaction);
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

    private CompanyAccounts getCompanyAccounts(String resourceLink, String requestId) throws HandlerException {

        CompanyAccounts accounts;

        try {
            accounts = accountsService.getCompanyAccounts(resourceLink, requestId);
        } catch (ServiceException e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(RESOURCE, resourceLink);
            LOG.errorContext(requestId,"Error in service layer when obtaining company-accounts data for resource: "
                    + resourceLink, e, logMap);
            throw new HandlerException(e.getMessage(), e.getCause());
        }

        return accounts;
    }

    /**
     * Get the company-account type from the links resource within the given company-accounts data object
     *
     * @param accountsData company-accounts resource data
     * @return the {@link AccountType} that exist in the given accounts data
     * @throws HandlerException if unable to find account type in accounts data
     */
    private AccountType getCompanyAccountType(CompanyAccounts accountsData) throws HandlerException {
        return accountsData.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase(LinkType.SELF.getLink()))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseThrow(() -> new HandlerException("Unable to find account type in account data" +
                        accountsData.getKind()));
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
        descriptionValues.put("period_end_on", DATE_TIME_FORMATTER_RESPONSE_DISPLAY
                .format(getCurrentPeriodEndOn(accountData)));

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
        return LocalDate.parse(date, DATE_TIME_FORMATTER_ISO);
    }
}
