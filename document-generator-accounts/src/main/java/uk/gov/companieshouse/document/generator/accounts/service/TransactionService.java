package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.api.model.transaction.Transaction;

public interface TransactionService {

    /**
     * Call the api sdk to get transaction of id passed.
     *
     * __NOTE:__: It is important to note, currently it will call an internal transaction manager
     * within this project that calls the transactions api through ERIC as the private sdk hasn't
     * been built yet. When the private SDK has been built (SFA-518, SFA-670), we should be able to simply delete the
     * transaction package and use the SDK in the implementation of this method.
     *
     * @return transaction
     */
    Transaction getTransaction(String id);
}
