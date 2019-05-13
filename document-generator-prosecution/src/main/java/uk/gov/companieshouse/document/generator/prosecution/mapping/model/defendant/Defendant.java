package uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public class Defendant {
    @JsonProperty("officer_id")
    private String officerId;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("person_officer_details")
    PersonOfficerDetails personOfficerDetails;

    @JsonProperty("company_officer_details")
    CompanyOfficerDetails companyOfficerDetails;

    @JsonProperty("date_appointed_on")
    private LocalDate dateAppointedOn;

    @JsonProperty("date_terminated_on")
    private LocalDate dateTerminatedOn;

    @JsonProperty("appointment_type")
    private String appointmentType;

    @JsonProperty("is_corporate_appointment")
    private Boolean isCorporateAppointment;

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PersonOfficerDetails getPersonOfficerDetails() {
        return personOfficerDetails;
    }

    public void setPersonOfficerDetails(PersonOfficerDetails personOfficerDetails) {
        this.personOfficerDetails = personOfficerDetails;
    }

    public CompanyOfficerDetails getCompanyOfficerDetails() {
        return companyOfficerDetails;
    }

    public void setCompanyOfficerDetails(CompanyOfficerDetails companyOfficerDetails) {
        this.companyOfficerDetails = companyOfficerDetails;
    }

    public LocalDate getDateAppointedOn() {
        return dateAppointedOn;
    }

    public void setDateAppointedOn(LocalDate dateAppointedOn) {
        this.dateAppointedOn = dateAppointedOn;
    }

    public LocalDate getDateTerminatedOn() {
        return dateTerminatedOn;
    }

    public void setDateTerminatedOn(LocalDate dateTerminatedOn) {
        this.dateTerminatedOn = dateTerminatedOn;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Boolean getIsCorporateAppointment() {
        return isCorporateAppointment;
    }

    public void setIsCorporateAppointment(Boolean isCorporateAppointment) {
        this.isCorporateAppointment = isCorporateAppointment;
    }
}
