package uk.gov.companieshouse.document.generator.accounts;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.transaction.Resource;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.AccountType;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class AccountsDocumentInfoServiceImpl implements DocumentInfoService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountsService accountsService;

    public static final String MODULE_NAME_SPACE = "document-generator-accounts";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Override
    public DocumentInfo getDocumentInfo() {
        LOG.info("Started getting document");

        String resource = "";
        String resourceId = "";

        Transaction transaction = transactionService.getTransaction(resource);
        if (transaction == null) {
            LOG.error("transaction not found");
            return null;
        }

        return Optional.of(transaction)
                .map(Transaction::getResources)
                .map(resources -> resources.get(resourceId))
                .map(Resource::getLinks)
                .map(links -> links.get("resource"))
                // when abridged has been migrated to use the company-accounts api, the code for the
                // company accounts should work for abridged, resulting in this abridged specific code
                // qualifying for removal
                .filter(this::isAbridged)
                .map(accountsLinks -> getAbridgedAccountData(resourceId))
                .orElse(null);

    }

    /**
     * Gets the abridged account data from abridged specific services (abridged-api).
     * @param resourceId - the abridged account resource link
     * @return DocumentInfo object containing information requested from document-generator-core
     */
    private DocumentInfo getAbridgedAccountData(String resourceId) {
        Accounts accounts = accountsService.getAccounts(resourceId);

        if (accounts == null) {
            return null;
        }

        AccountType accountsType = getAccountType(accounts);
        if (accountsType == null) {
            return null;
        }

        return new DocumentInfo();
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
     * determines whether it is an abridged link as "/transactions/{transactionId}/accounts/{accountsId}"
     * only exists within the abridged implementation
     * @param accountLink - account link
     * @return true if abridged, false if not
     */
    private boolean isAbridged(String accountLink) {
        return accountLink.matches("/transactions/\\d+(\\-\\d+)+/accounts/\\w+?=");
    }
}