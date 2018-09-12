package uk.gov.companieshouse.document.generator.accounts;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.transaction.Resource;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandler;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class AccountsDocumentInfoServiceImpl implements DocumentInfoService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccountsHandler accountsHandler;


    public static final String MODULE_NAME_SPACE = "document-generator-accounts";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) {
        LOG.info("Started getting document");

        String resourceId = documentInfoRequest.getResourceId();
        String resourceUri = documentInfoRequest.getResourceUri();

        Transaction transaction = transactionService.getTransaction(resourceId);
        if (transaction == null) {
            LOG.error("transaction not found");
            return null;
        }

        String resourceLink =  Optional.of(transaction)
                .map(Transaction::getResources)
                .map(resources -> resources.get(resourceId))
                .map(Resource::getLinks)
                .map(links -> links.get(LinkType.RESOURCE.getLink()))
                .orElseGet(() -> {
                    LOG.info("Unable to find resource: " + resourceId + " in transaction: " + resourceUri);
                    return "";
                });

        // when the Accounts migration has been completed to Company Accounts, this code can be removed
        if (isAccounts(resourceLink)) {
            return accountsHandler.getAccountsData(resourceLink);
         }

        return null;
    }

    /**
     * Gets the abridged account data from abridged specific services (abridged-api).
     * @param resourceId - the abridged account resource link
     * @return DocumentInfo object containing information requested from document-generator-core
     */
    private DocumentInfoResponse getAbridgedAccountData(String resourceId) {
        Accounts accounts = accountsService.getAccounts(resourceId);

        if (accounts == null) {
            return null;
        }

        AccountType accountsType = getAccountType(accounts);
        if (accountsType == null) {
            return null;
        }

        return new DocumentInfoResponse();
    }

    /**
     * Get the account type from the account link within links
     *
     * @return accountsData - accounts data
     */
    private AccountType getAccountType(Accounts accountsData) {
        return accountsData.getLinks().keySet()
                .stream()
                .filter(e -> !e.equalsIgnoreCase("self"))
                .map(AccountType::getAccountType)
                .findFirst()
                .orElseGet(() -> {
                    LOG.info("Unable to find account type in account data: " + accountsData.getId());
                    return null;
                });
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