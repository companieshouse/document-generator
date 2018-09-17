package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;

public interface AccountsService {

    /**
     * Call to private-sdk to get accounts resource data.
     *
     * __NOTE:__: It is important to note, currently it will call a temporary internal private-sdk
     * that makes a rest call to get the data requested. When the private SDK has been built
     * (SFA-518, SFA-670), we can replace the internal sdk solution with private-sdk calls.
     *
     * @return accounts - base accounts resource
     * @throws ServiceException - throws a service exception
     */
    Accounts getAccounts(String resource) throws ServiceException;
}
