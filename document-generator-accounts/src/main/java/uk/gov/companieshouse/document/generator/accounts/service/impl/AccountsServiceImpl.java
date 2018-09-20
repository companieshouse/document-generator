package uk.gov.companieshouse.document.generator.accounts.service.impl;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.AccountsManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private AccountsManager accountsManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Accounts getAccounts(String resource) throws ServiceException {
        try {
            LOG.info("Getting accounts data: " + resource);
            return accountsManager.getAccounts(resource);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbridgedAccountsApi getAbridgedAccounts(String resource) throws ServiceException {
        try {
            LOG.info("Getting abridged accounts data: " + resource);
            return accountsManager.getAbridgedAccounts(resource);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}