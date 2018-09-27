package uk.gov.companieshouse.document.generator.interfaces.model;

import com.google.gson.Gson;
import java.util.Objects;

/**
 * Represents the request to be sent to the implementation that uses getDocumentInfo
 */
public class DocumentInfoRequest {

    /**
     * The link to the resource
     */
    String resourceUri;
    /**
     * The id of the resource
     */
    String resourceId;
    /**
     * the mime type that the document should be rendered as
     */
    String mimeType;
    /**
     * The document or report type to be generated
     */
    String documentType;

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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentInfoRequest that = (DocumentInfoRequest) o;
        return Objects.equals(resourceUri, that.resourceUri) &&
                Objects.equals(resourceId, that.resourceId) &&
                Objects.equals(mimeType, that.mimeType) &&
                Objects.equals(documentType, that.documentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceUri, resourceId, mimeType, documentType);
    }
}
