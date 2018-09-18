package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

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
     * Get an Accounts resource data based from the resource link
     * @param resourceLink - resource link
     * @return - document info
     */
    DocumentInfoResponse getAccountsData(String resourceLink) throws HandlerException;
}
