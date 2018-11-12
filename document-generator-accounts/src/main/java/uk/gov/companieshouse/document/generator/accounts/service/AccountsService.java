package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;

public interface AccountsService {

    /**
     * Call to private-sdk to get accounts resource data.
     *
     * __NOTE:__: It is important to note, currently it will call a temporary internal private-sdk
     * that makes a rest call to get the data requested. When the private SDK has been built
     * (SFA-518, SFA-670), we can replace the internal sdk solution with private-sdk calls.
     *
     * @param resource resource link
     * @param requestId id of the request
     * @return base {@link Accounts} resource
     * @throws ServiceException throws a service exception
     */
    Accounts getAccounts(String resource, String requestId) throws ServiceException;

    /**
     * Call to private-sdk to get abridged accounts resource data.
     *
     * __NOTE:__: It is important to note, currently it will call a temporary internal private-sdk
     * that makes a rest call to get the data requested. When the private SDK has been built
     * (SFA-518, SFA-670), we can replace the internal sdk solution with private-sdk calls.
     *
     * @param resource resource link
     * @param requestId id of the request
     * @return {@link AbridgedAccountsApi} resource
     * @throws ServiceException throws a service exception
     */
    AbridgedAccountsApi getAbridgedAccounts(String resource, String requestId) throws ServiceException;

    /**
     * Call to private-sdk to get the smallFull accounts resource data and map it to the smallFullAccountsIXBRL model
     *
     * __NOTE:__: It is important to note, currently it will call a temporary internal private-sdk
     * that makes a rest call to get the data requested. When the private SDK has been built
     * (SFA-518, SFA-670), we can replace the internal sdk solution with private-sdk calls.
     *
     * @param resource resource link
     * @param requestId id of the request
     * @return {@link SmallFullAccountIxbrl} resource
     * @throws ServiceException throws a service exception
     */
    SmallFullAccountIxbrl getSmallFullAccounts(String resource, String requestId) throws ServiceException;
}
