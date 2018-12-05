package uk.gov.companieshouse.document.generator.api.service;

import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.api.exception.ServiceException;

import java.util.Map;

public interface DocumentTypeService {

    /**
     * get Document type from the resource uri
     *
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     * @return DocumentType type of document requested
     * @throws ServiceException
     */
    DocumentType getDocumentType(Map<String, String> requestParameters) throws ServiceException;
}
