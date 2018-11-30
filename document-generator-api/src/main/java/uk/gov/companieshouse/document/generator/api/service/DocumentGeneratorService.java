package uk.gov.companieshouse.document.generator.api.service;


import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;

public interface DocumentGeneratorService {

    /**
     * Generate a document
     *
     * @param documentRequest object that contains the request details
     * @param requestId the requestId
     * @return Response object containing the response and response status
     */
    ResponseObject generate(DocumentRequest documentRequest, String requestId);
}
