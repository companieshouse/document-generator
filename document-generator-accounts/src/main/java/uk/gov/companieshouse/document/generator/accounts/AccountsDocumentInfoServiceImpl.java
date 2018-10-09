package uk.gov.companieshouse.document.generator.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandler;
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
    private AccountsHandler accountsHandler;

    public static final String MODULE_NAME_SPACE = "document-generator-accounts";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {
        LOG.info("Started getting document");

        String resourceId = documentInfoRequest.getResourceId();
        String resourceUri = documentInfoRequest.getResourceUri();

        final Map< String, Object > debugMap = new HashMap< >();
        debugMap.put("resource_uri", resourceUri);
        debugMap.put("resource_id", resourceId);

        Transaction transaction;
        try {
            transaction = transactionService.getTransaction(resourceId);
        } catch (ServiceException e) {
            LOG.error(e, debugMap);
            throw new DocumentInfoException("Failed to get transaction with resourceId: " + resourceId, e);
        }

        String resourceLink =  Optional.of(transaction)
                .map(Transaction::getResources)
                .map(resources -> resources.get(resourceUri))
                .map(Resources::getLinks)
                .map(links -> links.get(LinkType.RESOURCE.getLink()))
                .orElseGet(() -> {
                    LOG.info("Unable to find resource: " + resourceId + " in transaction: " + resourceUri);
                    return "";
                });


        // when the Accounts migration has been completed to Company Accounts, this code can be removed
        if (isAccounts(resourceLink)) {
            try {
                return accountsHandler.getAbridgedAccountsData(transaction, resourceLink);
            } catch (HandlerException e) {
                LOG.error(e, debugMap);
                throw new DocumentInfoException("Failed to get abridged data for transaction: "
                        + transaction.getId() + " and resource link: " + resourceLink);
            }
        }

        return null;
    }

    /**
     * Determines if is an accounts specific link as "/transactions/{transactionId}/accounts/{accountsId}"
     * only exists within the accounts (abridged) implementation
     * @param resourceLink - resource link
     * @return true if accounts, false if not
     */
    private boolean isAccounts(String resourceLink) {
        return resourceLink.matches("/transactions\\/[0-9-]+/accounts\\/.*");
    }
}