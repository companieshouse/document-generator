package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InsolvencyCase {

    @JsonProperty("case_number")
    private Long number;

    @JsonProperty("case_type")
    private String type;

    @JsonProperty("practitioners")
    private List<Practitioner> practitioners;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Practitioner> getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(
        List<Practitioner> practitioners) {
        this.practitioners = practitioners;
    }
}
