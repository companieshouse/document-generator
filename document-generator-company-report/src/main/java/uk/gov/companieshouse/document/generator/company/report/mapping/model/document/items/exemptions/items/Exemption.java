package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exemption {

    @JsonProperty("exemption_type")
    private String exemptionType;

    @JsonProperty("items")
    private List<ExemptionItems> items;

    @JsonProperty("moved_on_date_formatted")
    private String movedOnDateFormatted;

    public String getExemptionType() {
        return exemptionType;
    }

    public void setExemptionType(String exemptionType) {
        this.exemptionType = exemptionType;
    }

    public List<ExemptionItems> getItems() {
        return items;
    }

    public void setItems(List<ExemptionItems> items) {
        this.items = items;
    }

    public String getMovedOnDateFormatted() {
        return movedOnDateFormatted;
    }

    public void setMovedOnDateFormatted(String movedOnDateFormatted) {
        this.movedOnDateFormatted = movedOnDateFormatted;
    }
}
