package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.Address;

import java.time.LocalDate;

public class Practitioner {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("appointed_on")
    private LocalDate appointedOn;

    @JsonProperty("ceased_to_act_on")
    private LocalDate ceasedToActOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(
        Address address) {
        this.address = address;
    }

    public LocalDate getAppointedOn() {
        return appointedOn;
    }

    public void setAppointedOn(LocalDate appointedOn) {
        this.appointedOn = appointedOn;
    }

    public LocalDate getCeasedToActOn() {
        return ceasedToActOn;
    }

    public void setCeasedToActOn(LocalDate ceasedToActOn) {
        this.ceasedToActOn = ceasedToActOn;
    }
}
