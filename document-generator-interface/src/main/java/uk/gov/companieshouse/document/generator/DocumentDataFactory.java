package uk.gov.companieshouse.document.generator;

import uk.gov.companieshouse.document.generator.docdatatype.DocumentDataType;

public interface DocumentDataFactory {
    public GetDocumentInfo getDocumentInfo(DocumentDataType documentDataType);
}
