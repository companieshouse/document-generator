package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Register {

    @JsonProperty("items")
    private List<RegisterItems> items;

    @JsonProperty("register_type")
    private String registerType;

    @JsonProperty("information")
    private String information;

    public List<RegisterItems> getItems() {
        return items;
    }

    public void setItems(List<RegisterItems> items) {
        this.items = items;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
