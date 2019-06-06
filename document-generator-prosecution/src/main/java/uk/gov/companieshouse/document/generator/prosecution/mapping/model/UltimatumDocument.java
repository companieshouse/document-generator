package uk.gov.companieshouse.document.generator.prosecution.mapping.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UltimatumDocument extends ProsecutionDocument {
    @JsonProperty("CompanyName")
    private String companyName = "Zain's company";

    @JsonProperty("CompanyNumber")
    private String companyNumber = "1234";

    @JsonProperty("DefendantName")
    private String defendantName = "Zain";

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getDefendantName() {
        return defendantName;
    }

    public void setDefendantName(String defendantName) {
        this.defendantName = defendantName;
    }
}
