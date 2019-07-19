package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateOfBirth {

    @JsonProperty("month")
    private String month;

    @JsonProperty("year")
    private Long year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
