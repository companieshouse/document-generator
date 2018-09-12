package uk.gov.companieshouse.document.generator.core.service;


import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;

public interface DocumentGeneratorService {

    /**
     * generate the document
     *
     * @param documentRequest
     * @return A DocumentReponse
     */
    DocumentResponse generate(DocumentRequest documentRequest);
}
