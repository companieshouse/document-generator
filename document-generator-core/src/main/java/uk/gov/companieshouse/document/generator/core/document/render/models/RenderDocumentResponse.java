package uk.gov.companieshouse.document.generator.core.document.render.models;

import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;

public class RenderDocumentResponse {

    private DocumentGenerationCompleted generatedDocument;

    private int status;

    public RenderDocumentResponse(){}

    public RenderDocumentResponse(DocumentGenerationCompleted generatedDocument, int status) {
        this.generatedDocument = generatedDocument;
        this.status = status;
    }

    public DocumentGenerationCompleted getGeneratedDocument() {
        return generatedDocument;
    }

    public void setGeneratedDocument(DocumentGenerationCompleted generatedDocument) {
        this.generatedDocument = generatedDocument;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
