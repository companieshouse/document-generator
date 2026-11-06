package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContributionSubType {

    @JsonProperty("sub_type")
    private String subType;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
