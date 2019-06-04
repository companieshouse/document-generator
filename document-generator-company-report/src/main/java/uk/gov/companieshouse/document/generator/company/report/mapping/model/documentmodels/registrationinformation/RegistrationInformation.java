package uk.gov.companieshouse.document.generator.company.report.mapping.model.documentmodels.registrationinformation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.documentmodels.registrationinformation.items.CompanyType;

@JsonInclude(Include.NON_NULL)
public class RegistrationInformation {

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date_of_incorporation")
    private LocalDate dateOfIncorporation;

    @JsonProperty("registered_office")
    private RegisteredOffice registeredOffice;

    @JsonProperty("company_type")
    private CompanyType companyType;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("nature_of_business")
    private List<SicCodes> natureOfBusiness;

    public LocalDate getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(LocalDate dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public RegisteredOffice getRegisteredOffice() {
        return registeredOffice;
    }

    public void setRegisteredOffice(RegisteredOffice registeredOffice) {
        this.registeredOffice = registeredOffice;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<SicCodes> getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(List<SicCodes> natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
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
}
