package uk.gov.companieshouse.document.generator.core.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import javax.validation.constraints.NotNull;

public class DocumentRequest {

    @NotNull
    @JsonProperty("content_type")
    private String contentType;

    @NotNull
    @JsonProperty("document_type")
    private String documentType;

    @NotNull
    @JsonProperty("id")
    private String id;

    @NotNull
    @JsonProperty("resource")
    private String resource;

    @NotNull
    @JsonProperty("resource_id")
    private String resourceId;

    @NotNull
    @JsonProperty("user_id")
    private String userId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}