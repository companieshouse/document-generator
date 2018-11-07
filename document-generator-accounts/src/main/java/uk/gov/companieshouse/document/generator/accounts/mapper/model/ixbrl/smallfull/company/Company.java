package uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class Company {

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("jurisdiction")
    private String jurisdiction;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(getCompanyNumber(), company.getCompanyNumber()) &&
                Objects.equals(getCompanyName(), company.getCompanyName()) &&
                Objects.equals(getJurisdiction(), company.getJurisdiction());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCompanyNumber(), getCompanyName(), getJurisdiction());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
