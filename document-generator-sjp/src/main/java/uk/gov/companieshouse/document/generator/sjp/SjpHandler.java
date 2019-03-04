package uk.gov.companieshouse.document.generator.sjp;

import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
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
