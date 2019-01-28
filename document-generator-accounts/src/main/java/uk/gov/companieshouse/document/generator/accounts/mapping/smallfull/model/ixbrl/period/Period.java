package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Period {

    @JsonProperty("current_period_start_on")
    private String currentPeriodStartOn;

    @JsonProperty("current_period_end_on")
    private String currentPeriodEndsOn;

    @JsonProperty("previous_period_start_on")
    private String previousPeriodStartOn;

    @JsonProperty("previous_period_end_on")
    private String previousPeriodEndsOn;

    @JsonProperty("current_period_start_on_formatted")
    private String currentPeriodStartOnFormatted;

    @JsonProperty("current_period_end_on_formatted")
    private String currentPeriodEndOnFormatted;

    @JsonProperty("previous_period_start_on_formatted")
    private String previousPeriodStartOnFormatted;

    @JsonProperty("previous_period_end_on_formatted")
    private String previousPeriodEndOnFormatted;

    @JsonProperty("current_period_bs_date")
    private String currentPeriodBSDate;

    @JsonProperty("previous_period_bs_date")
    private String previousPeriodBSDate;

    public String getCurrentPeriodStartOn() {
        return currentPeriodStartOn;
    }

    public void setCurrentPeriodStartOn(String currentPeriodStartOn) {
        this.currentPeriodStartOn = currentPeriodStartOn;
    }

    public String getCurrentPeriodEndsOn() {
        return currentPeriodEndsOn;
    }

    public void setCurrentPeriodEndsOn(String currentPeriodEndsOn) {
        this.currentPeriodEndsOn = currentPeriodEndsOn;
    }

    public String getPreviousPeriodStartOn() {
        return previousPeriodStartOn;
    }

    public void setPreviousPeriodStartOn(String previousPeriodStartOn) {
        this.previousPeriodStartOn = previousPeriodStartOn;
    }

    public String getPreviousPeriodEndsOn() {
        return previousPeriodEndsOn;
    }

    public void setPreviousPeriodEndsOn(String previousPeriodEndsOn) {
        this.previousPeriodEndsOn = previousPeriodEndsOn;
    }

    public String getCurrentPeriodStartOnFormatted() {
        return currentPeriodStartOnFormatted;
    }

    public void setCurrentPeriodStartOnFormatted(String currentPeriodStartOnFormatted) {
        this.currentPeriodStartOnFormatted = currentPeriodStartOnFormatted;
    }

    public String getCurrentPeriodEndOnFormatted() {
        return currentPeriodEndOnFormatted;
    }

    public void setCurrentPeriodEndOnFormatted(String currentPeriodEndOnFormatted) {
        this.currentPeriodEndOnFormatted = currentPeriodEndOnFormatted;
    }

    public String getPreviousPeriodStartOnFormatted() {
        return previousPeriodStartOnFormatted;
    }

    public void setPreviousPeriodStartOnFormatted(String previousPeriodStartOnFormatted) {
        this.previousPeriodStartOnFormatted = previousPeriodStartOnFormatted;
    }

    public String getPreviousPeriodEndOnFormatted() {
        return previousPeriodEndOnFormatted;
    }

    public void setPreviousPeriodEndOnFormatted(String previousPeriodEndOnFormatted) {
        this.previousPeriodEndOnFormatted = previousPeriodEndOnFormatted;
    }

    public String getCurrentPeriodBSDate() {
        return currentPeriodBSDate;
    }

    public void setCurrentPeriodBSDate(String currentPeriodBSDate) {
        this.currentPeriodBSDate = currentPeriodBSDate;
    }

    public String getPreviousPeriodBSDate() {
        return previousPeriodBSDate;
    }

    public void setPreviousPeriodBSDate(String previousPeriodBSDate) {
        this.previousPeriodBSDate = previousPeriodBSDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;
        Period period = (Period) o;
        return Objects.equals(getCurrentPeriodStartOn(), period.getCurrentPeriodStartOn()) &&
                Objects.equals(getCurrentPeriodEndsOn(), period.getCurrentPeriodEndsOn()) &&
                Objects.equals(getPreviousPeriodStartOn(), period.getPreviousPeriodStartOn()) &&
                Objects.equals(getPreviousPeriodEndsOn(), period.getPreviousPeriodEndsOn()) &&
                Objects.equals(getCurrentPeriodStartOnFormatted(), period.getCurrentPeriodStartOnFormatted()) &&
                Objects.equals(getCurrentPeriodEndOnFormatted(), period.getCurrentPeriodEndOnFormatted()) &&
                Objects.equals(getPreviousPeriodStartOnFormatted(), period.getPreviousPeriodStartOnFormatted()) &&
                Objects.equals(getPreviousPeriodEndOnFormatted(), period.getPreviousPeriodEndOnFormatted()) &&
                Objects.equals(getCurrentPeriodBSDate(), period.getCurrentPeriodBSDate()) &&
                Objects.equals(getPreviousPeriodBSDate(), period.getPreviousPeriodBSDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrentPeriodStartOn(), getCurrentPeriodEndsOn(), getPreviousPeriodStartOn(),
                getPreviousPeriodEndsOn(), getCurrentPeriodStartOnFormatted(), getCurrentPeriodEndOnFormatted(),
                getPreviousPeriodStartOnFormatted(), getPreviousPeriodEndOnFormatted(), getCurrentPeriodBSDate(),
                getPreviousPeriodBSDate());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
