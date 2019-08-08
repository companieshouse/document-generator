package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonEntitled {

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
