package uk.gov.companieshouse.document.generator.api.service;

import uk.gov.companieshouse.document.generator.api.exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;

public interface DocumentTypeService {

    /**
     * get Document type from the resource uri
     *
     * @param resourceUri the resource uri
     * @return DocumentType type of document requested
     * @throws DocumentGeneratorServiceException
     */
    DocumentType getDocumentType(String resourceUri) throws DocumentGeneratorServiceException;
}
