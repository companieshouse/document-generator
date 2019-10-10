package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociatedFilings {

    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("capital_description")
    private String capitalDescription;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCapitalDescription() {
        return capitalDescription;
    }

    public void setCapitalDescription(String capitalDescription) {
        this.capitalDescription = capitalDescription;
    }
}
