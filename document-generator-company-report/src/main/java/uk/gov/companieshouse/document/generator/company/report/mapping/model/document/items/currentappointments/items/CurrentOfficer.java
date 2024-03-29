package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.common.ContactDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.DateDayMonthYear;


public class CurrentOfficer {

    @JsonProperty("officer_role")
    private String officerRole;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("appointed_on")
    private String appointed;

    @JsonProperty("resigned_on")
    private String resigned;

    @JsonProperty("date_of_birth")
    private DateDayMonthYear dateOfBirth;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("occupation")
    private String occupation;

    @JsonProperty("country_of_residence")
    private String countryOfResidence;

    @JsonProperty("appointed_before")
    private LocalDate appointedBefore;

    @JsonProperty("is_pre_1992_appointment")
    private Boolean isPre92;

    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("service_address")
    private Address serviceAddress;

    @JsonProperty("other_forenames")
    private String otherForenames;

    @JsonProperty("service_address_is_same_as_registered_office_address")
    private Boolean  serviceAddressSameAsOfficeAddress;

    @JsonProperty("identification")
    private Identification identification;

    @JsonProperty("principal_office_address")
    private Address principalOfficeAddress;

    @JsonProperty("responsibilities")
    private String responsibilities;

    @JsonProperty("contact_details")
    private ContactDetails contactDetails;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean getServiceAddressSameAsOfficeAddress() {
        return serviceAddressSameAsOfficeAddress;
    }

    public void setServiceAddressSameAsOfficeAddress(Boolean serviceAddressSameAsOfficeAddress) {
        this.serviceAddressSameAsOfficeAddress = serviceAddressSameAsOfficeAddress;
    }

    public LocalDate getAppointedBefore() {
        return appointedBefore;
    }

    public void setAppointedBefore(LocalDate appointedBefore) {
        this.appointedBefore = appointedBefore;
    }

    public Boolean getPre92() {
        return isPre92;
    }

    public void setPre92(Boolean pre92) {
        isPre92 = pre92;
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

    public Address getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(Address serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getOtherForenames() {
        return otherForenames;
    }

    public void setOtherForenames(String otherForenames) {
        this.otherForenames = otherForenames;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public void setOfficerRole(String officerRole) {
        this.officerRole = officerRole;
    }

    public String getResigned() {
        return resigned;
    }

    public void setResigned(String resigned) {
        this.resigned = resigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAppointed() {
        return appointed;
    }

    public void setAppointed(String appointed) {
        this.appointed = appointed;
    }

    public DateDayMonthYear getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateDayMonthYear dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public Address getPrincipalOfficeAddress() {
        return principalOfficeAddress;
    }

    public void setPrincipalOfficeAddress(Address principalOfficeAddress) {
        this.principalOfficeAddress = principalOfficeAddress;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }
}
