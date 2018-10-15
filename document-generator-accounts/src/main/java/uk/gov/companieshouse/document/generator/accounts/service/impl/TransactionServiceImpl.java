package uk.gov.companieshouse.document.generator.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.TransactionManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionManager transactionManager;

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransaction(String id) throws ServiceException {
        try {
            LOG.info("Getting transaction data: " + id);
            return transactionManager.getTransaction(id);
        } catch (Exception e) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("id", id);
            LOG.errorContext("Failed to get transaction data: " + id, e, logMap);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
