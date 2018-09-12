package uk.gov.companieshouse.document.generator.core.document.render.models;

import com.google.gson.Gson;

public class RenderDocumentResponse {

    private String location;

    private String documentSize;

    public RenderDocumentResponse(){}

    public RenderDocumentResponse(String location, String documentSize) {
        this.location = location;
        this.documentSize = documentSize;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
