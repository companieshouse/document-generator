package uk.gov.companieshouse.document.generator.core.document.render.models;

import com.google.gson.Gson;

public class RenderDocumentRequest {

    private String templateName;

    private String assetId;

    private String contentType;

    private String documentType;

    private String location;

    private String data;

    public RenderDocumentRequest(){}

    public RenderDocumentRequest(String templateName, String assetId, String contentType, String documentType, String location, String data) {
        this.templateName = templateName;
        this.assetId = assetId;
        this.contentType = contentType;
        this.documentType = documentType;
        this.location = location;
        this.data = data;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
