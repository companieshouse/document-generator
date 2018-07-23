package uk.gov.companieshouse.document.generator;

import uk.gov.companieshouse.document.generator.core.document.doctype.DocumentInfoType;

public interface DocumentInfoFactory {
    public GetDocumentInfo getDocumentInfo(DocumentInfoType documentDataType);
}
