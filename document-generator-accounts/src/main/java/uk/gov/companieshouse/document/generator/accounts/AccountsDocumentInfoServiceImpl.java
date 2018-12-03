package uk.gov.companieshouse.document.generator.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AbridgedAccountsDataHandler;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.SmallFullAccountsDataHandler;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountsDocumentInfoServiceImpl implements DocumentInfoService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    AbridgedAccountsDataHandler abridgedAccountsDataHandler;

    @Autowired
    SmallFullAccountsDataHandler smallFullAccountsDataHandler;

    public static final String MODULE_NAME_SPACE = "document-generator-accounts";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String ABRIDGED = "abridged";

    private static final String SMALL_FULL = "smallfull";

    private static final String ABRIDGED_REGEX = "/transactions\\/[0-9-]+/accounts\\/.*";

    private static final String SMALL_FULL_REGEX = "/transactions\\/[0-9-]+/company-accounts\\/.*";

    private static final String ERROR_CALLING_ACCOUNTS = "An error occurred when calling: %s handler to obtain: %s" +
             " accounts data for resourceUri: %s";

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {

        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();

        final Map< String, Object > debugMap = new HashMap< >();
        debugMap.put("resource_uri", resourceUri);

        LOG.infoContext(requestId,"Started getting document info", debugMap);

        String accountType = "";
        try {
            if (resourceUri.matches(ABRIDGED_REGEX)) {
                accountType = ABRIDGED;
                return abridgedAccountsDataHandler.getAbridgedAccountsData(resourceUri, requestId);

            } else if (resourceUri.matches(SMALL_FULL_REGEX)) {
                accountType = SMALL_FULL;
                return smallFullAccountsDataHandler.getSmallFullAccountsData(resourceUri, requestId);
            } else {
                throw new DocumentInfoException("No Matching account type was located for resourceUri: "
                        + resourceUri);
            }
        } catch (HandlerException e) {
            LOG.errorContext(requestId, String.format(ERROR_CALLING_ACCOUNTS, accountType, accountType, resourceUri),
                e, debugMap);
            throw new DocumentInfoException("Failed to get " + accountType + " data for resourceUri: "
                + resourceUri);
        }
    }
}