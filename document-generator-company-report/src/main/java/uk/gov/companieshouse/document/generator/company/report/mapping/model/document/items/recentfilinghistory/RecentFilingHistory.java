package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class RecentFilingHistory {


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date")
    private String date;

    @JsonProperty("form")
    private String form;

    @JsonProperty("description")
    private String description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
