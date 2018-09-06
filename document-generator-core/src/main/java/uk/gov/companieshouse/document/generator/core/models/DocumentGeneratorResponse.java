package uk.gov.companieshouse.document.generator.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DocumentGeneratorResponse {

    @JsonProperty("description")
    private String description;

    @JsonProperty("description_identifier")
    private String descriptionIdentifier;

    @JsonProperty("description_values")
    private Map<String, String> descriptionValues;

    @JsonProperty("location")
    private String location;

    @JsonProperty("size")
    private String size;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
