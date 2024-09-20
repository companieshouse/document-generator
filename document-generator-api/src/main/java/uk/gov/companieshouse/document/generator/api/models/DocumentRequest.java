package uk.gov.companieshouse.document.generator.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import jakarta.validation.constraints.NotNull;

public class DocumentRequest {

    @NotNull
    @JsonProperty("resource_uri")
    private String resourceUri;

    /**
     * resource id will be removed
     */
    @Deprecated
    @JsonProperty("resource_id")
    private String resourceId;

    @NotNull
    @JsonProperty("mime_type")
    private String mimeType;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("is_public_location_required")
    private boolean isPublicLocationRequired;

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean isPublicLocationRequired() {
        return isPublicLocationRequired;
    }

    public void setPublicLocationRequired(boolean publicLocationRequired) {
        isPublicLocationRequired = publicLocationRequired;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
