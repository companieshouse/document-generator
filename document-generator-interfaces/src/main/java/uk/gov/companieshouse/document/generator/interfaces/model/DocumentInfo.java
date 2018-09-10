package uk.gov.companieshouse.document.generator.interfaces.model;

import com.google.gson.Gson;

import java.util.Map;

public class DocumentInfo {
    
    String data;
    String templateName;
    String assetId;
    String location;
    String Description;
    String DescriptionIdentifier;
    Map<String, String> descriptionValues;

    public DocumentInfo() {}

    public DocumentInfo(String data, String templateName, String assetId, String location, String description,
                        String descriptionIdentifier, Map<String, String> descriptionValues) {
        this.data = data;
        this.templateName = templateName;
        this.assetId = assetId;
        this.location = location;
        Description = description;
        DescriptionIdentifier = descriptionIdentifier;
        this.descriptionValues = descriptionValues;
    }

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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionIdentifier() {
        return DescriptionIdentifier;
    }

    public void setDescriptionIdentifier(String descriptionIdentifier) {
        DescriptionIdentifier = descriptionIdentifier;
    }

    public Map<String, String> getDescriptionValues() {
        return descriptionValues;
    }

    public void setDescriptionValues(Map<String, String> descriptionValues) {
        this.descriptionValues = descriptionValues;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
