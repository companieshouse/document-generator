package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.Address;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.DateOfBirth;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.Identification;

public class Psc {

    @JsonProperty("address")
    private Address address;

    @JsonProperty("ceased_on")
    private String ceasedOn;

    @JsonProperty("country_of_residence")
    private String countryOfResidence;

    @JsonProperty("date_of_birth")
    private DateOfBirth dateOfBirth;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("natures_of_control")
    private List<NaturesOfControl> naturesOfControl;

    @JsonProperty("notified_on")
    private String notifiedOn;

    @JsonProperty("identification")
    private Identification identification;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<NaturesOfControl> getNaturesOfControl() {
        return naturesOfControl;
    }

    public void setNaturesOfControl(List<NaturesOfControl> naturesOfControl) {
        this.naturesOfControl = naturesOfControl;
    }

    public String getCeasedOn() {
        return ceasedOn;
    }

    public void setCeasedOn(String ceasedOn) {
        this.ceasedOn = ceasedOn;
    }

    public String getNotifiedOn() {
        return notifiedOn;
    }

    public void setNotifiedOn(String notifiedOn) {
        this.notifiedOn = notifiedOn;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}










