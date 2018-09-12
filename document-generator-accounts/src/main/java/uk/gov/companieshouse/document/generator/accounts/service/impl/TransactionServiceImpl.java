package uk.gov.companieshouse.document.generator.accounts.service.impl;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.TransactionManager;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransaction(String id) {
        LOG.info("Getting data from transactions-api");

        ResponseEntity<Transaction> transaction = TransactionManager.getTransaction(id);

        if (transaction.getStatusCode() != HttpStatus.OK) {
            LOG.error(String.format("Failed to retrieve data from AP with status code %s: %s", transaction.getStatusCode().toString(), id));
            return null;
        }
        LOG.trace("Transaction data retrieved successfully");
        return transaction.getBody();
    }
}
