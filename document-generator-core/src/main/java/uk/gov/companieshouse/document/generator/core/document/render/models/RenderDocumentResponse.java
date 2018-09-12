package uk.gov.companieshouse.document.generator.core.document.render.models;

import com.google.gson.Gson;

public class RenderDocumentResponse {

    private String location;

    private String documentSize;

    private int status;

    public RenderDocumentResponse(){}

    public RenderDocumentResponse(String location, String documentSize, int status) {
        this.location = location;
        this.documentSize = documentSize;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(String documentSize) {
        this.documentSize = documentSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
