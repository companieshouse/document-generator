package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormattedDates {

    @JsonProperty("formatted_date")
    private String formattedDate;

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
