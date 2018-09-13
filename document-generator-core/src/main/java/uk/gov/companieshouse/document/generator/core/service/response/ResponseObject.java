package uk.gov.companieshouse.document.generator.core.service.response;

import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;

public class ResponseObject  {

    private ResponseStatus status;

    private DocumentResponse documentResponse;

    public ResponseObject(ResponseStatus status) {
        this.status = status;
    }

    public ResponseObject(ResponseStatus status, DocumentResponse documentResponse) {
        this.status = status;
        this.documentResponse = documentResponse;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public DocumentResponse getData() {
        return documentResponse;
    }

    public void setData(DocumentResponse data) {
        this.documentResponse = documentResponse;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
