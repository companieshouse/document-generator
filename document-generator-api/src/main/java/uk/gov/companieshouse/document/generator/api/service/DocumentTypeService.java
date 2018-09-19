package uk.gov.companieshouse.document.generator.api.service;

import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;

public interface DocumentTypeService {

    /**
     * get Document type by pattern matching the resourceUri to a regex pattern
     *
     * @param resourceUri
     * @return
     */
    String getDocumentType(String resourceUri) throws DocumentGeneratorServiceException;
}
