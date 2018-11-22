package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;

import java.util.Objects;

public class AbridgedAccountsApiData {

    @JsonProperty("abridged_accounts")
    private AbridgedAccountsApi abridgedAccountsApi;

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    public AbridgedAccountsApi getAbridgedAccountsApi() {
        return abridgedAccountsApi;
    }

    public void setAbridgedAccountsApi(AbridgedAccountsApi abridgedAccountsApi) {
        this.abridgedAccountsApi = abridgedAccountsApi;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbridgedAccountsApiData)) return false;
        AbridgedAccountsApiData that = (AbridgedAccountsApiData) o;
        return Objects.equals(getAbridgedAccountsApi(), that.getAbridgedAccountsApi()) &&
                Objects.equals(getCompanyNumber(), that.getCompanyNumber()) &&
                Objects.equals(getCompanyName(), that.getCompanyName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAbridgedAccountsApi(), getCompanyNumber(), getCompanyName());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
