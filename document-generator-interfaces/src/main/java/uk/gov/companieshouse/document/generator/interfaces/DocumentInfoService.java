package uk.gov.companieshouse.document.generator.interfaces;

import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
 * A service to get {@Link DocumentInfoResponse} for GenerateDocument
 */
public interface DocumentInfoService {

    /**
     * Get the given {@link DocumentInfoResponse}
     * @param documentInfoRequest - document info request
     * @return DocumentInfo
     */
    DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException;
}