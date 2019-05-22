package uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public class Offence {

    @JsonProperty("filing_period_type")
    private FilingPeriodType filingPeriodType;

    @JsonProperty("status")
    private OffenceStatus status;

    @JsonProperty("filing_period_ends_on")
    private LocalDate filingPeriodEndsOn;

    @JsonProperty("filing_due_on")
    private LocalDate filingDueOn;

    @JsonProperty("filing_period_id")
    private String filingPeriodId;

    public FilingPeriodType getFilingPeriodType() {
        return filingPeriodType;
    }

    public void setFilingPeriodType(FilingPeriodType filingPeriodType) {
        this.filingPeriodType = filingPeriodType;
    }

    public OffenceStatus getStatus() {
        return status;
    }

    public void setStatus(OffenceStatus status) {
        this.status = status;
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

    public String getFilingPeriodId() {
        return filingPeriodId;
    }

    public void setFilingPeriodId(String periodId) {
        this.filingPeriodId = periodId;
    }
}
