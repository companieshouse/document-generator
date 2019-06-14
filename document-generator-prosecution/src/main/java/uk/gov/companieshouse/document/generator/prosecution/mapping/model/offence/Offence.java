package uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public class Offence {

    @JsonProperty("FilingPeriodType")
    private FilingPeriodType filingPeriodType;

    @JsonProperty("FilingPeriodEndsOn")
    private LocalDate filingPeriodEndsOn;

    @JsonProperty("FilingDueOn")
    private LocalDate filingDueOn;

    public FilingPeriodType getFilingPeriodType() {
        return filingPeriodType;
    }

    public void setFilingPeriodType(FilingPeriodType filingPeriodType) {
        this.filingPeriodType = filingPeriodType;
    }

    public LocalDate getFilingPeriodEndsOn() {
        return filingPeriodEndsOn;
    }

    public void setFilingPeriodEndsOn(LocalDate periodEndsOn) {
        this.filingPeriodEndsOn = periodEndsOn;
    }

    public LocalDate getFilingDueOn() {
        return filingDueOn;
    }

    public void setFilingDueOn(LocalDate filingDueOn) {
        this.filingDueOn = filingDueOn;
    }
}
