package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.loanstodirectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalInformation {

    public AdditionalInformation(String details) {
        this.details = details;
    }

    @JsonProperty("details")
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
