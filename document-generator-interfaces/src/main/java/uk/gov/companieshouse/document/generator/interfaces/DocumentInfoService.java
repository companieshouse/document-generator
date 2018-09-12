package uk.gov.companieshouse.document.generator.interfaces;

import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
 * A service to get {@Link DocumentInfo} for GenerateDocument
 */
public interface DocumentInfoService {
    /**
     * Get the given {@link DocumentInfoResponse}
     * @return DocumentInfo
     */
    DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest);
}
