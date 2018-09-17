package uk.gov.companieshouse.document.generator.core.service;


import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.service.response.ResponseObject;

public interface DocumentGeneratorService {

    /**
     * generate the document
     *
     * @param documentRequest
     * @return A DocumentReponse
     */
    ResponseObject generate(DocumentRequest documentRequest, String requestId);
}
