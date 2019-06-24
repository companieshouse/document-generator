package uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ProsecutionCase {

    @JsonProperty("CompanyIncorporationNumber")
    private String companyIncorporationNumber;

    @JsonProperty("CompanyName")
    private String companyName;

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
}
