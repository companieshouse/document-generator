package uk.gov.companieshouse.document.generator.api.service;


import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;

public interface DocumentGeneratorService {

    /**
     * generate the document
     *
     * @param documentRequest
     * @return A DocumentReponse
     */
    ResponseObject generate(DocumentRequest documentRequest, String requestId);
}
