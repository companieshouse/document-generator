package uk.gov.companieshouse.document.generator.core.service;

import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorResponse;

public interface DocumentGeneratorService {

    //TODO service interface created impl to be done as part of SFA 664
    /**
     * generate the document
     *
     * @param documentGeneratorRequest
     * @return
     */
    DocumentGeneratorResponse generate(DocumentGeneratorRequest documentGeneratorRequest);
}
