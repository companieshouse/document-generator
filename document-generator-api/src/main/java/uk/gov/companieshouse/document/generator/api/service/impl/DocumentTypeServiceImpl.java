package uk.gov.companieshouse.document.generator.api.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;

import java.util.Arrays;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentType getDocumentType(String resourceUri) throws DocumentGeneratorServiceException {

        return Arrays.stream(DocumentType.values())
                .filter(docTypeEntry -> resourceUri.matches(docTypeEntry.getPattern()))
                .findFirst().orElseThrow(() -> new DocumentGeneratorServiceException(
                        "Could not locate the document type from the Uri"));
    }
}
