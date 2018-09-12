package uk.gov.companieshouse.document.generator.accounts;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.transaction.Resource;
import uk.gov.companieshouse.api.model.transaction.Transaction;
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

        return Optional.of(transaction)
                .map(Transaction::getResources)
                .map(resources -> resources.get(resourceUri))
                .map(Resource::getLinks)
                .map(links -> links.get("resource"))
                // when abridged has been migrated to use the company-accounts api, the code for the
                // company accounts should work for abridged, resulting in this abridged specific code
                // qualifying for removal
                .filter(this::isAbridged)
                .map(accountsLinks -> new DocumentInfoResponse())
                .orElse(null);

    }

    /**
     * determines whether it is an abridged link as "/transactions/{transactionId}/accounts/{accountsId}"
     * only exists within the abridged implementation
     * @param accountLink - account link
     * @return true if abridged, false if not
     */
    private boolean isAbridged(String accountLink) {
        return accountLink.matches("/transactions\\/[0-9\\-]+\\/accounts\\/.*");
    }
}