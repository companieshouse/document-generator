package uk.gov.companieshouse.document.generator.company.report.mapping.model.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("company_report")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
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
