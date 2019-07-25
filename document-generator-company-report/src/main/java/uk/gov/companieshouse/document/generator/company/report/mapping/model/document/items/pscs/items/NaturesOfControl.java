package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class NaturesOfControl {

    @JsonProperty("natures_of_control")
    private String naturesOfControl;

    @JsonProperty("natures_of_control_descriptions")
    private String naturesOfControlDescription;

    public String getNaturesOfControl() {
        return naturesOfControl;
    }

    public void setNaturesOfControl(String naturesOfControl) {
        this.naturesOfControl = naturesOfControl;
    }

    public String getNaturesOfControlDescription() {
        return naturesOfControlDescription;
    }

    public void setNaturesOfControlDescription(String naturesOfControlDescription) {
        this.naturesOfControlDescription = naturesOfControlDescription;
    }
}
