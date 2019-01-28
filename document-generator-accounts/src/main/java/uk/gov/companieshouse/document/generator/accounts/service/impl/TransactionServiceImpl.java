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
    public Transaction getTransaction(String id, String requestId) throws ServiceException {

        try {
            LOG.infoContext(requestId,"Getting transaction data: " + id, getDebugMap(id));
            return transactionManager.getTransaction(id, requestId);
        } catch (Exception e) {
            LOG.errorContext(requestId,"Failed to get transaction data: " + id, e, getDebugMap(id));
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private Map<String, Object> getDebugMap(String id) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("id", id);

        return debugMap;
    }
}
