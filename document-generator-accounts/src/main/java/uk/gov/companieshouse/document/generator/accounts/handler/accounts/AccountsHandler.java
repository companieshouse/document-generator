package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
 * AccountsHandler handles all 'Accounts' resources. When used under doc-gen-accounts, Accounts refers
 * to the base Accounts resource and/or Abridged Accounts resource. Additionally, this handler shall
 * carry out any processing relating to Accounts resources whether it be service layer calls to retrieve
 * data or formatting that data in order to return to doc-gen-api
 */
public interface AccountsHandler {

    /**
     * Get an Abridged accounts resource from the given resource link
     * @param transaction the transaction data
     * @param resourceLink the resource link of the abridged accounts
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    DocumentInfoResponse getAbridgedAccountsData(Transaction transaction, String resourceLink) throws HandlerException;
}
