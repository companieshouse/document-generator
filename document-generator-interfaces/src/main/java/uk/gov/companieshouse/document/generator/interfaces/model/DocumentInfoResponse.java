package uk.gov.companieshouse.document.generator.interfaces.model;

import com.google.gson.Gson;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the response from the implementation that implements getDocumentInfo
 */
public class DocumentInfoResponse {

    /**
     * The data to be rendered to the document
     */
    String data;
    /**
     * The name of the template to be used
     */
    String templateName;
    /**
     * The identifier of the asset within the assets registry
     */
    String assetId;
    /**
     * The unique path to be added to the location the document is to be stored
     */
    String path;
    /**
     * The identifier for the document description
     */
    String descriptionIdentifier;
    /**
     * A map of key/value pairs used to complete the looked up description identifier enum
     */
    Map<String, String> descriptionValues;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescriptionIdentifier() {
        return descriptionIdentifier;
    }

    public void setDescriptionIdentifier(String descriptionIdentifier) {
        this.descriptionIdentifier = descriptionIdentifier;
    }

    public Map<String, String> getDescriptionValues() {
        return descriptionValues;
    }

    public void setDescriptionValues(Map<String, String> descriptionValues) {
        this.descriptionValues = descriptionValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentInfoResponse that = (DocumentInfoResponse) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(templateName, that.templateName) &&
                Objects.equals(assetId, that.assetId) &&
                Objects.equals(path, that.path) &&
                Objects.equals(descriptionIdentifier, that.descriptionIdentifier) &&
                Objects.equals(descriptionValues, that.descriptionValues);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(data, templateName, assetId, path, descriptionIdentifier,
                        descriptionValues);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}