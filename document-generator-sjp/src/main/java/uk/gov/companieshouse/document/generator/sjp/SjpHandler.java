package uk.gov.companieshouse.document.generator.sjp;

import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
 * AccountsHandler handles all 'Accounts' resources. When used under doc-gen-accounts, Accounts refers
 * to the base Accounts resource and/or Abridged Accounts resource. Additionally, this handler shall
 * carry out any processing relating to Accounts resources whether it be service layer calls to retrieve
 * data or formatting that data in order to return to doc-gen-api
 */
public interface SjpHandler {

    /**
     * Get an Abridged accounts resource from the given resource link
     * @param transaction the transaction data
     * @param resourceLink the resource link of the abridged accounts
     * @return a populated {@link DocumentInfoResponse} object
     * @throws HandlerException throws a custom handler exception
     */
    DocumentInfoResponse getProsecutionData(Transaction transaction, String resourceLink) throws Exception;
}
