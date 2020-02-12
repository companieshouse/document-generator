package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Director {

    public Director(String name) {
        this.name = name;
    }

    @JsonProperty("index")
    private int index;

    @JsonProperty("name")
    private String name;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
