package uk.gov.companieshouse.document.generator.core.service;

import uk.gov.companieshouse.document.generator.core.service.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.service.models.DocumentResponse;

public interface DocumentGeneratorService {

    /**
     * generate the document
     *
     * @param documentRequest
     * @return
     */
    DocumentResponse generate(DocumentRequest documentRequest);
}
