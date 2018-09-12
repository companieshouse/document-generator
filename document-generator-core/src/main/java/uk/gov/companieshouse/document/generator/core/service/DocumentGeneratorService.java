package uk.gov.companieshouse.document.generator.core.service;

import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;

public interface DocumentGeneratorService {

    //TODO service interface created impl to be done as part of SFA 664
    /**
     * generate the document
     *
     * @param documentRequest
     * @return
     */
    DocumentResponse generate(DocumentRequest documentRequest);
}
