package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class DirectorsReportStatements {

    @JsonProperty("additional_information")
    private String additionalInformation;

    @JsonProperty("company_policy_on_disabled_employees")
    private String companyPolicyOnDisabledEmployees;

    @JsonProperty("political_and_charitable_donations")
    private String politicalAndCharitableDonations;

    @JsonProperty("principal_activities")
    private String principalActivities;

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getCompanyPolicyOnDisabledEmployees() {
        return companyPolicyOnDisabledEmployees;
    }

    public void setCompanyPolicyOnDisabledEmployees(String companyPolicyOnDisabledEmployees) {
        this.companyPolicyOnDisabledEmployees = companyPolicyOnDisabledEmployees;
    }

    public String getPoliticalAndCharitableDonations() {
        return politicalAndCharitableDonations;
    }

    public void setPoliticalAndCharitableDonations(String politicalAndCharitableDonations) {
        this.politicalAndCharitableDonations = politicalAndCharitableDonations;
    }

    public String getPrincipalActivities() {
        return principalActivities;
    }

    public void setPrincipalActivities(String principalActivities) {
        this.principalActivities = principalActivities;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorsReportStatements that = (DirectorsReportStatements) o;
        return Objects.equals(additionalInformation, that.additionalInformation) &&
                Objects.equals(companyPolicyOnDisabledEmployees, that.companyPolicyOnDisabledEmployees) &&
                Objects.equals(politicalAndCharitableDonations, that.politicalAndCharitableDonations) &&
                Objects.equals(principalActivities, that.principalActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(additionalInformation, companyPolicyOnDisabledEmployees, politicalAndCharitableDonations, principalActivities);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
