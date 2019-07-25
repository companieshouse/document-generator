package uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ProsecutionCase {

    @JsonProperty("CompanyIncorporationNumber")
    private String companyIncorporationNumber;

    @JsonProperty("CompanyName")
    private String companyName;

    @JsonProperty("CaseReferenceNumber")
    private String caseReferenceNumber;

    public String getCompanyIncorporationNumber() {
        return companyIncorporationNumber;
    }

    public void setCompanyIncorporationNumber(String companyIncorporationNumber) {
        this.companyIncorporationNumber = companyIncorporationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCaseReferenceNumber() {
        return caseReferenceNumber;
    }

    public void setCaseReferenceNumber(String caseReferenceNumber) {
        this.caseReferenceNumber = caseReferenceNumber;
    }
}
