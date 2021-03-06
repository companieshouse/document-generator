package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.Address;

public class Practitioner {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("appointed_on")
    private String appointedOn;

    @JsonProperty("ceased_to_act_on")
    private String ceasedToActOn;

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

    public String getAppointedOn() {
        return appointedOn;
    }

    public void setAppointedOn(String appointedOn) {
        this.appointedOn = appointedOn;
    }

    public String getCeasedToActOn() {
        return ceasedToActOn;
    }

    public void setCeasedToActOn(String ceasedToActOn) {
        this.ceasedToActOn = ceasedToActOn;
    }
}
