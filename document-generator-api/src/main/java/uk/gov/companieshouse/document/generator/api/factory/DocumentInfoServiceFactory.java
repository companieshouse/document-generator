package uk.gov.companieshouse.document.generator.api.factory;

import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;

public interface DocumentInfoServiceFactory {

    /**
     * Factory interface to select Implementation to use when generating a document
     *
     * @param documentType The type of document being generated
     * @return DocumentInfoService the correct implementation of the service
     */
    DocumentInfoService get(String documentType);
}
