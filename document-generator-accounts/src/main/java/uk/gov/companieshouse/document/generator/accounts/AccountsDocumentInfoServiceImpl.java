package uk.gov.companieshouse.document.generator.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
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
import java.util.Optional;

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

    private static final String ERROR_CALLING_ACCOUNTS = "An error occurred when calling: %s handler to obtain: %s" +
             " accounts data for transaction: %s and resource link: %s";

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {

        String resourceId = documentInfoRequest.getResourceId();
        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();

        final Map< String, Object > debugMap = new HashMap< >();
        debugMap.put("resource_uri", resourceUri);
        debugMap.put("resource_id", resourceId);

        LOG.infoContext(requestId,"Started getting document info", debugMap);

        Transaction transaction;
        try {
            transaction = transactionService.getTransaction(resourceId, requestId);
        } catch (ServiceException e) {
            LOG.errorContext(requestId,"An error occurred when calling the transaction service with resource id: "
                    + resourceId, e, debugMap);
            throw new DocumentInfoException("Failed to get transaction with resourceId: " + resourceId, e);
        }

        String resourceLink =  Optional.of(transaction)
                .map(Transaction::getResources)
                .map(resources -> resources.get(resourceUri))
                .map(Resources::getLinks)
                .map(links -> links.get(LinkType.RESOURCE.getLink()))
                .orElseGet(() -> {
                    LOG.infoContext(requestId,"Unable to find resource: " + resourceId
                            + " in transaction: " + resourceUri, debugMap);
                    return "";
                });


        // Will need to be refactored when additional accounts are added.
        String accountType = "";
        try {
            if (isAbridgedAccounts(resourceLink)) {
                accountType = ABRIDGED;
                return abridgedAccountsDataHandler.getAbridgedAccountsData(transaction, resourceLink, requestId);
            } else {
                accountType = SMALL_FULL;
                return smallFullAccountsDataHandler.getSmallFullAccountsData(transaction, resourceLink, requestId);
            }
        } catch (HandlerException e) {
        LOG.errorContext(requestId, String.format(ERROR_CALLING_ACCOUNTS, accountType, accountType, transaction.getId(),
                resourceLink), e, debugMap);
        throw new DocumentInfoException("Failed to get " + accountType + " data for transaction: "
                + transaction.getId() + " and resource link: " + resourceLink);
        }

    }

    /**
     * Determines if is an accounts specific link as "/transactions/{transactionId}/accounts/{accountsId}"
     * only exists within the accounts (abridged) implementation
     * @param resourceLink - resource link
     * @return true if accounts, false if not
     */
    private boolean isAbridgedAccounts(String resourceLink) {
        return resourceLink.matches("/transactions\\/[0-9-]+/accounts\\/.*");
    }
}