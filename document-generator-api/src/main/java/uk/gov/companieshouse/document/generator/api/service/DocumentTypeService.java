package uk.gov.companieshouse.document.generator.api.service;

import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;

public interface DocumentTypeService {

    /**
     * get Document type
     *
     * @param resourceUri
     * @return String value of document type
     */
    DocumentType getDocumentType(String resourceUri) throws DocumentGeneratorServiceException;
}
