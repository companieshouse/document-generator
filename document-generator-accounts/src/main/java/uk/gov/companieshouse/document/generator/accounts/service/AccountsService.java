package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.api.model.accounts.Accounts;

public interface AccountsService {

    /**
     * Call the api sdk to get accounts data of resource passed.
     *
     * __NOTE:__: It is important to note, currently it will call an internal accounts manager
     * within this project that calls the abridged api through ERIC as the private sdk hasn't
     * been built yet. When the private SDK has been built (SFA-518, SFA-670), we should be able to simply delete the
     * accounts package and use the SDK in the implementation of this method.
     *
     * @return accounts
     */
    Accounts getAccounts(String resource);

}

