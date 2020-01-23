package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
}
