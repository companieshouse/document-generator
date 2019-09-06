package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterItems {

    @JsonProperty("moved_on")
    private String movedOn;

    @JsonProperty("register_moved_to")
    private String registerMovedTo;

    @JsonProperty("formatted_date")
    private String formattedDate;

    public String getMovedOn() {
        return movedOn;
    }

    public void setMovedOn(String movedOn) {
        this.movedOn = movedOn;
    }

    public String getRegisterMovedTo() {
        return registerMovedTo;
    }

    public void setRegisterMovedTo(String registerMovedTo) {
        this.registerMovedTo = registerMovedTo;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
