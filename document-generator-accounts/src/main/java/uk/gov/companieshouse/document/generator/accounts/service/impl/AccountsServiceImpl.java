package uk.gov.companieshouse.document.generator.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.AccountsManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsManager accountsManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Accounts getAccounts(String resource, String requestId) throws ServiceException {
        try {
            LOG.infoContext(requestId, "Getting accounts data: " + resource, getDebugMap(resource));
            return accountsManager.getAccounts(resource);
        } catch (URIValidationException | ApiErrorResponseException e) {
            LOG.errorContext(requestId,"Failed to retrieve accounts data: ", e, getDebugMap(resource));
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbridgedAccountsApi getAbridgedAccounts(String resource, String requestId) throws ServiceException {
        try {
            LOG.infoContext(requestId, "Getting abridged accounts data: " + resource, getDebugMap(resource));
            return accountsManager.getAbridgedAccounts(resource);
        } catch (URIValidationException | ApiErrorResponseException e) {
            LOG.errorContext(requestId,"Failed to retrieve abridged accounts data: ", e, getDebugMap(resource));
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public SmallFullAccountIxbrl getSmallFullAccounts(String resource, String requestId) throws ServiceException {
        try {
            LOG.infoContext(requestId, "Getting smallFull accounts data: " + resource, getDebugMap(resource));
            return accountsManager.getSmallFullAccounts(resource);
        } catch (URIValidationException | ApiErrorResponseException e) {
            LOG.errorContext(requestId,"Failed to retrieve smallFull accounts data: ", e, getDebugMap(resource));
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private Map<String, Object> getDebugMap(String resource) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("resource", resource);

        return debugMap;
    }
}