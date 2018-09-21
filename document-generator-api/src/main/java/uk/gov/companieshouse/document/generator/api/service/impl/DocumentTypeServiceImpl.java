package uk.gov.companieshouse.document.generator.api.service.impl;

import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;

import java.util.Arrays;

public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Override
    public DocumentType getDocumentType(String resourceUri) throws DocumentGeneratorServiceException {

        return Arrays.stream(DocumentType.values())
                .filter(docTypeEntry -> resourceUri.matches(docTypeEntry.getPattern()))
                .findFirst().orElseThrow(() -> new DocumentGeneratorServiceException(
                        "Could not locate the document type from the Uri"));
    }
}
