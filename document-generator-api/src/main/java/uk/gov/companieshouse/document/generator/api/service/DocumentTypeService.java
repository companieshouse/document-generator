package uk.gov.companieshouse.document.generator.api.service;

import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.api.exception.DocumentGeneratorServiceException;

import java.util.Map;

public interface DocumentTypeService {

    /**
     * get Document type from the resource uri
     *
     * @param requestParameters Map containing requestId, resourceId and resourceUri as a key/value pair
     * @return DocumentType type of document requested
     * @throws DocumentGeneratorServiceException
     */
    DocumentType getDocumentType(Map<String, String> requestParameters) throws DocumentGeneratorServiceException;
}
