package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.CompanyType;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.RegisteredOffice;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.SicCodes;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.Status;

import java.util.List;

@JsonInclude(Include.NON_NULL)
public class RegistrationInformation {

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("date_of_incorporation")
    private String dateOfIncorporation;

    @JsonProperty("date_of_dissolution")
    private String dateOfDissolution;

    @JsonProperty("registered_office")
    private RegisteredOffice registeredOffice;

    @JsonProperty("service_address")
    private RegisteredOffice serviceAddress;

    @JsonProperty("company_type")
    private CompanyType companyType;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("nature_of_business")
    private List<SicCodes> natureOfBusiness;

    @JsonProperty("date_of_incorporation_label")
    private String dateOfIncorporationLabel;
  
    @JsonProperty("external_registration_number")
    private String externalRegistrationNumber;

    @JsonProperty("foreign_company_details")
    private ForeignCompanyDetails foreignCompanyDetails;

    public String getDateOfIncorporationLabel() {
        return dateOfIncorporationLabel;
    }

    public void setDateOfIncorporationLabel(String dateOfIncorporationLabel) {
        this.dateOfIncorporationLabel = dateOfIncorporationLabel;
    }

    public String getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(String dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getDateOfDissolution() {
        return dateOfDissolution;
    }

    public void setDateOfDissolution(String dateOfDissolution) {
        this.dateOfDissolution = dateOfDissolution;
    }

    public RegisteredOffice getRegisteredOffice() {
        return registeredOffice;
    }

    public void setRegisteredOffice(RegisteredOffice registeredOffice) {
        this.registeredOffice = registeredOffice;
    }

    public RegisteredOffice getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(RegisteredOffice serviceAddress) {
        this.serviceAddress = serviceAddress;
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

    public String getExternalRegistrationNumber() {
        return externalRegistrationNumber;
    }

    public void setExternalRegistrationNumber(String externalRegistrationNumber) {
        this.externalRegistrationNumber = externalRegistrationNumber;
    }

    public ForeignCompanyDetails getForeignCompanyDetails() {
        return foreignCompanyDetails;
    }

    public void setForeignCompanyDetails(ForeignCompanyDetails foreignCompanyDetails) {
        this.foreignCompanyDetails = foreignCompanyDetails;
    }
}
