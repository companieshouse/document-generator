package uk.gov.companieshouse.document.generator.accounts.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.AccountsManager;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger("document-generator-accounts");

    /**
     * {@inheritDoc}
     */
    @Override
    public Accounts getAccounts(String resource) {
        LOG.info("Getting accounts data from abridged-accounts-api");

        ResponseEntity<Accounts> accounts = AccountsManager.getAccounts(resource);

        if (accounts.getStatusCode() != HttpStatus.OK) {
            LOG.error("Failed to retrieve data from API: " + resource);
            return null;
        }
        LOG.trace("Accounts data retrieved successfully");
        return accounts.getBody();
    }
}
