package uk.gov.companieshouse.document.generator.api.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.Arrays;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentType getDocumentType(String resourceUri) throws DocumentGeneratorServiceException {

        LOG.info("Getting the document type from resource: " + resourceUri);
        return Arrays.stream(DocumentType.values())
                .filter(docTypeEntry -> resourceUri.matches(docTypeEntry.getPattern()))
                .findFirst().orElseThrow(() -> new DocumentGeneratorServiceException(
                        "Could not locate the document type from the Uri"));
    }
}
