package uk.gov.companieshouse.document.generator.api.service.impl;

import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;

import java.util.HashMap;
import java.util.Map;

public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Override
    public String getDocumentType(String resourceUri) throws DocumentGeneratorServiceException {

        Map<String, String> docType = new HashMap<>();
        docType.put("ACCOUNTS", "/transactions\\/.*\\/(?:company-)?accounts\\/.*");

        return docType.entrySet().stream()
                .filter(docTypeEntry -> resourceUri.matches(docTypeEntry.getValue()))
                .map(docTypeEntry -> docTypeEntry.getKey())
                .findFirst().orElseThrow(() -> new DocumentGeneratorServiceException(
                        "Could not locate the document type from the Uri"));
    }
}
