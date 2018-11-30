package uk.gov.companieshouse.document.generator.accounts.service;


import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;

public interface TransactionService {

    /**
     * Call the api sdk to get transaction of id passed.
     *
     * __NOTE:__: It is important to note, currently it will call an internal transaction manager
     * within this project that calls the transactions api through ERIC as the private sdk hasn't
     * been built yet. When the private SDK has been built, we should be able to simply delete the
     * transaction package and use the SDK in the implementation of this method.
     *
     * @param id id of transaction
     * @param requestId id of the request
     * @return {@link Transaction} data
     * @throws ServiceException throw if the service layer is thrown
     */
    Transaction getTransaction(String id, String requestId) throws ServiceException;
}
