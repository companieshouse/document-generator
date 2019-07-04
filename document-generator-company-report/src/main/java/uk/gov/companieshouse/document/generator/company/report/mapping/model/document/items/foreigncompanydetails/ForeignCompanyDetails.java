package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ForeignCompanyDetails {

    @JsonProperty("country")
    private String country;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("legal_form")
    private String legalForm;

    @JsonProperty("name")
    private String name;

    @JsonProperty("governed_by")
    private String governedBy;

    @JsonProperty("business_activity")
    private String businessActivity;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernedBy() {
        return governedBy;
    }

    public void setGovernedBy(String governedBy) {
        this.governedBy = governedBy;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }
}

