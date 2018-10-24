package uk.gov.companieshouse.document.generator.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

    @JsonProperty("location")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
