package uk.gov.companieshouse.document.generator.core.document.render;

public class RenderDocumentReponse {

    private DocumentGenerationCompleted generatedDocument;

    private int status;

    public RenderDocumentReponse(){}

    public RenderDocumentReponse(DocumentGenerationCompleted generatedDocument, int status) {
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
