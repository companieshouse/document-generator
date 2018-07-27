package uk.gov.companieshouse.document.generator.interfaces;

import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;

/**
 * A service to get {@Link DocumentInfo} for GenerateDocument
 */
public interface DocumentInfoService {
    /**
     * Get the given {@link DocumentInfo}
     * @return DocumentInfo
     */
    DocumentInfo getDocumentInfo();
}
