package uk.gov.companieshouse.document.generator.interfaces.model;

import com.google.gson.Gson;
import java.util.Map;
import java.util.Objects;

public class DocumentInfoResponse {

    String data;
    String templateName;
    String assetId;
    String location;
    String description;
    String descriptionIdentifier;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                Objects.equals(location, that.location) &&
                Objects.equals(description, that.description) &&
                Objects.equals(descriptionIdentifier, that.descriptionIdentifier) &&
                Objects.equals(descriptionValues, that.descriptionValues);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(data, templateName, assetId, location, description, descriptionIdentifier,
                        descriptionValues);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}