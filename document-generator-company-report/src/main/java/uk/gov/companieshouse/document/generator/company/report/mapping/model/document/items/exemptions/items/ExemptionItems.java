package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExemptionItems {

    @JsonProperty("exempt_from")
    private String exemptFrom;

    @JsonProperty("exempt_to")
    private String exemptTo;

    public String getExemptFrom() {
        return exemptFrom;
    }

    public void setExemptFrom(String exemptFrom) {
        this.exemptFrom = exemptFrom;
    }

    public String getExemptTo() {
        return exemptTo;
    }

    public void setExemptTo(String exemptTo) {
        this.exemptTo = exemptTo;
    }
}
