package uk.gov.companieshouse.document.generator.accounts.mapping.cic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Approval {

    @JsonProperty("name")
    private String name;

    @JsonProperty("date")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
