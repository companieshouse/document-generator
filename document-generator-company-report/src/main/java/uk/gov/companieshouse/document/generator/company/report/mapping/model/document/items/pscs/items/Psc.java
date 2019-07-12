package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.common.DateOfBirth;
import uk.gov.companieshouse.api.model.psc.Identification;

public class Psc {

    @JsonProperty("address")
    private Address address;

    @JsonProperty("ceased_on")
    private LocalDate ceasedOn;

    @JsonProperty("country_of_residence")
    private String countryOfResidence;

    @JsonProperty("date_of_birth")
    private DateOfBirth dateOfBirth;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("natures_of_control")
    private String[] naturesOfControl;

    @JsonProperty("notified_on")
    private LocalDate notifiedOn;

    @JsonProperty("identification")
    private Identification identification;

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCeasedOn(LocalDate ceasedOn) {
        this.ceasedOn = ceasedOn;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public void setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setNaturesOfControl(String[] naturesOfControl) {
        this.naturesOfControl = naturesOfControl;
    }

    public void setNotifiedOn(LocalDate notifiedOn) {
        this.notifiedOn = notifiedOn;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}










