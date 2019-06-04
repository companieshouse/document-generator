package uk.gov.companieshouse.document.generator.company.report.mapping.model.documentmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.documentmodels.registrationinformation.RegistrationInformation;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("company_report")
public class CompanyReport {

    @JsonProperty("company_registration_information")
    private RegistrationInformation registrationInformation;

    public RegistrationInformation getRegistrationInformation() {
        return registrationInformation;
    }

    public void setRegistrationInformation(RegistrationInformation registrationInformation) {
        this.registrationInformation = registrationInformation;
    }
}
