package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Company {

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("jurisdiction")
    private String jurisdiction;

    @JsonProperty("is_lbg")
    private Boolean isLBG;

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Boolean getIsLBG() { return isLBG; }

    public void setIsLBG(Boolean LBG) { isLBG = LBG; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(getCompanyNumber(), company.getCompanyNumber()) &&
                Objects.equals(getCompanyName(), company.getCompanyName()) &&
                Objects.equals(getJurisdiction(), company.getJurisdiction()) &&
                Objects.equals(getIsLBG(), company.getIsLBG());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCompanyNumber(), getCompanyName(), getJurisdiction(), getIsLBG());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
