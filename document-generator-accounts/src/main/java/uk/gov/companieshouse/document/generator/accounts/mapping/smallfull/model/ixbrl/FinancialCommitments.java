package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinancialCommitments {

    @JsonProperty("details")
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
