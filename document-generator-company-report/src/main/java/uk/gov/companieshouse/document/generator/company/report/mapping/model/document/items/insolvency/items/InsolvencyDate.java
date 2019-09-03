package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsolvencyDate {

    @JsonProperty("date")
    private String date;

    @JsonProperty("type")
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
